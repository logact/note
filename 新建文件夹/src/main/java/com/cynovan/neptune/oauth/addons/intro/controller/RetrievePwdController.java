package com.cynovan.neptune.oauth.addons.intro.controller;

import com.cynovan.neptune.oauth.addons.intro.service.RegisterService;
import com.cynovan.neptune.oauth.addons.intro.service.ValidateUserRegService;
import com.cynovan.neptune.oauth.base.arch.bean.CheckMessage;
import com.cynovan.neptune.oauth.base.database.jdo.QUserInfo;
import com.cynovan.neptune.oauth.base.utils.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/retrievePwd")
public class RetrievePwdController {


    @Autowired
    private ValidateUserRegService veriCodeService;

    @Autowired
    private RegisterService registerService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/sendCode")
    public String sendCode(@RequestParam String account) {
        CheckMessage result = new CheckMessage();
        if (StringLib.isEmpty(account) || QUserInfo.findByAccount(account) == null) {
            result.setSuccess(false);
            result.addMessage("用户不存在");
        } else {
            result.setSuccess(true);
            registerService.sendCheckCode(account);
            result.addMessage("验证码发送成功");
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/confirmModify")
    public String confirmModify(@RequestParam String account, @RequestParam String code, @RequestParam String pwd) {
        CheckMessage result = new CheckMessage();
        if (!veriCodeService.checkCode(account, code)) {
            result.setSuccess(false);
            result.addMessage("验证码错误");
            return result.toString();
        }
        Document userInfo = QUserInfo.findByAccount(account);
        if (userInfo != null) {
            DBUtilsNoCompany.updateOne(QUserInfo.collectionName,
                    DocumentLib.newDoc("id", DocumentLib.getID(userInfo)),
                    DocumentLib.new$Set(QUserInfo.password, DigestLib.getPasswordEncoder().encode(pwd)));
            result.addMessage("密码修改成功，2秒后自动转到登录页面");
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/bindMobile")
    public String bindMobile(@RequestParam String key, @RequestParam String code) {
        CheckMessage result = new CheckMessage();
        Document userInfo = UserUtils.getUser();
        if (veriCodeService.checkCode(key, code)) {
            userInfo.put(QUserInfo.mobile, key);
            DBUtilsCompany.save(QUserInfo.collectionName, userInfo);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/bindMail")
    public String bindMail(@RequestParam String key, @RequestParam String code) {
        CheckMessage result = new CheckMessage();
        Document userInfo = UserUtils.getUser();
        if (veriCodeService.checkCode(key, code)) {
            userInfo.put(QUserInfo.email, key);
            DBUtilsCompany.save(QUserInfo.collectionName, userInfo);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result.toString();
    }
}

