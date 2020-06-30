package com.cynovan.neptune.oauth.addons.intro.service;

import com.cynovan.neptune.oauth.addons.intro.jdo.QUserRegisterChecker;
import com.cynovan.neptune.oauth.base.arch.base.controller.BaseService;
import com.cynovan.neptune.oauth.base.config.bean.InitializeData;
import com.cynovan.neptune.oauth.base.jms.MessageService;
import com.cynovan.neptune.oauth.base.jms.document.QMailMessage;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.RequestLib;
import com.cynovan.neptune.oauth.base.utils.SMSLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class RegisterService extends BaseService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ValidateUserRegService veriCodeService;

    public boolean checkCode(String account, String code) {
        return veriCodeService.checkCode(account, code);
    }

    public void sendUserRegisterEmail(Document user, Document checker, HttpServletRequest request) {
        String userEmail = DocumentLib.getString(user, "email");
        if (StringLib.isNotEmpty(userEmail)) {
            String userName = DocumentLib.getString(user, "name");
            Document mailMessage = new Document();
            mailMessage.put(QMailMessage.subject, "欢迎您注册工业设备物联云平台，请验证邮箱身份");
            mailMessage.put(QMailMessage.sentDate, new Date());
            String url = RequestLib.getCompleteUrl(request, "intro/#/joingroup?code=", DocumentLib.getString(checker, "code2"));
            String text = InitializeData.getTemplate("user_register_checker_email_template");
            text = String.format(text, userName, url);
            mailMessage.put(QMailMessage.text, text);
            mailMessage.put(QMailMessage.to, DocumentLib.newList(userEmail));
            messageService.sendMail(mailMessage);
        } else {
            String mobile = DocumentLib.getString(checker, "mobile");
            String captcha = ValidateUserRegService.genernateCode(mobile);
            SMSLib.sendValidateCode(mobile, captcha);
            checker.put(QUserRegisterChecker.captcha, captcha);
        }
    }

    public void sendCheckCode(String account) {
        if (StringLib.contains(account, "@")) {
            String code = veriCodeService.genernateCode(account);
            Document mailMessage = new Document();
            mailMessage.put(QMailMessage.subject, "工业设备物联云平台");
            mailMessage.put(QMailMessage.sentDate, new Date());

            String text = InitializeData.getTemplate("neptune_checkcode_mail_template");
            text = String.format(text, code);
            mailMessage.put(QMailMessage.text, text);
            mailMessage.put(QMailMessage.to, DocumentLib.newList(account));
            messageService.sendMail(mailMessage);
        } else {
            SMSLib.sendValidateCode(account, ValidateUserRegService.genernateCode(account));
        }
    }
}
