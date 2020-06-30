package com.cynovan.neptune.oauth.addons.intro.controller;

import com.cynovan.neptune.oauth.addons.intro.jdo.QCompany;
import com.cynovan.neptune.oauth.addons.intro.jdo.QCompanyApply;
import com.cynovan.neptune.oauth.addons.intro.jdo.QCompanyInvite;
import com.cynovan.neptune.oauth.addons.intro.jdo.QUserRegisterChecker;
import com.cynovan.neptune.oauth.addons.intro.service.RegisterService;
import com.cynovan.neptune.oauth.base.arch.bean.CheckMessage;
import com.cynovan.neptune.oauth.base.database.jdo.QUserInfo;
import com.cynovan.neptune.oauth.base.message.MessageLib;
import com.cynovan.neptune.oauth.base.message.dto.MessageDto;
import com.cynovan.neptune.oauth.base.utils.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @ResponseBody
    @RequestMapping(value = "toJoinCompanyChecker/{code}")
    public String toJoinCompanyChecker(@PathVariable("code") String code, HttpServletRequest request) {
        CheckMessage checkMessage = CheckMessage.newInstance();
        Document userRegisterChecker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                DocumentLib.newDoc("code2", code));
        String userId = DocumentLib.getString(userRegisterChecker, QUserRegisterChecker.user_id);
        Document userInfo = DBUtilsNoCompany.findByID(QUserInfo.collectionName, userId);
        String company_id = DocumentLib.getString(userInfo, QUserInfo.company_id);
        boolean expired = DocumentLib.getBoolean(userRegisterChecker, QUserRegisterChecker.expired);
        if (expired == false || StringLib.isEmpty(company_id)) {
            String mobile = DocumentLib.getString(userRegisterChecker, QUserRegisterChecker.mobile);
            checkMessage.addData("invited", StringLib.isNotEmpty(mobile));
            if (StringLib.isNotEmpty(mobile)) {
                // 检查是否有手机邀请码
                List<Document> invite = DBUtilsNoCompany.list(QCompanyInvite.collectionName, DocumentLib.newDoc(QCompanyInvite.mobile, mobile), DocumentLib.newDoc(QCompanyInvite.code, 1));
                checkMessage.addData("invited", CollectionUtils.isNotEmpty(invite));
            }

            boolean validate = DocumentLib.getBoolean(userInfo, QUserInfo.validate);
            if (!validate) {
                DBUtilsNoCompany.updateOne(QUserInfo.collectionName, DocumentLib.newDoc("id", userId),
                        DocumentLib.new$Set(QUserInfo.validate, true));
            }

            if (expired == false) {
                DBUtilsNoCompany.updateOne(QUserRegisterChecker.collectionName,
                        DocumentLib.newDoc("code2", code),
                        DocumentLib.new$Set(QUserRegisterChecker.expired, true));
            }
            checkMessage.addData("type", "checked");
            checkMessage.addData("checker", userRegisterChecker);
        } else {
            checkMessage.addData("type", "expired");
        }
        return checkMessage.toString();
    }

    @ResponseBody
    @RequestMapping(value = "loadCheckerData/{code}")
    public String loadCheckerData(@PathVariable("code") String code) {
        CheckMessage checkMessage = CheckMessage.newInstance();
        Document userRegisterChecker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                DocumentLib.newDoc("code1", code));
        /*验证码在DB中存储，返回前端的时候要remove掉，不然被用户看到，可以恶意注册*/
        userRegisterChecker.remove("captcha");
        checkMessage.addData("checker", userRegisterChecker);
        return checkMessage.toString();
    }

    /**
     * 创建企业
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "createCompany")
    public String createCompany(HttpServletRequest request) {
        CheckMessage checkMessage = CheckMessage.newInstance();
        String companyName = request.getParameter(QCompany.name);
        /*Check the Company Name*/
        Document company = DBUtilsNoCompany.find(QCompany.collectionName,
                DocumentLib.newDoc(QCompany.name, companyName));
        if (company != null) {
            checkMessage.setSuccess(false);
            return checkMessage.toString();
        }

        company = new Document();
        company.put(QCompany.name, companyName);
        company.put(QCompany.email, request.getParameter(QCompany.email));
        company.put(QCompany.mobile, request.getParameter(QCompany.mobile));
        company.put(QCompany.type, request.getParameter(QCompany.type));
        company.put(QCompany.device_amount, 10);
        company.put("create_date", new Date());
        String code2 = request.getParameter(QUserRegisterChecker.code2);

        Document checker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                DocumentLib.newDoc("code2", code2));
        if (checker != null) {
            company.put(QCompany.user_id, DocumentLib.getString(checker, QCompany.user_id));
        }
        DBUtilsCompany.save(QCompany.collectionName, company);
        String id = DocumentLib.getID(company);

        /*update the company appid*/
        String appid = StringLib.join("nep", StringLib.substring(DigestLib.md5Hex(id), -9));
        String appsecret = StringLib.join("nep", id, System.currentTimeMillis());
        appsecret = DigestLib.md5Hex(appsecret);
        Document developer = new Document();
        developer.put("appid", appid);
        developer.put("appsecret", appsecret);
        DBUtilsNoCompany.updateOne(QCompany.collectionName, Filters.eq("_id", id),
                DocumentLib.new$Set("developer", developer));

        String token = DigestLib.md5Hex(id);
        DBUtilsNoCompany.updateOne(QCompany.collectionName, DocumentLib.newDoc("id", id),
                DocumentLib.new$Set(DocumentLib.newDoc("token", token)));

        Bson userQuery = Filters.eq("id", DocumentLib.getString(checker, "user_id"));
        // 默认管理员权限
        Document defaultSecurity = DocumentLib.newDoc();
        defaultSecurity.put("type", "admin");
        defaultSecurity.put("deviceControlPolicy", "3");
        defaultSecurity.put("deviceGroup", "target");
        defaultSecurity.put("targetGroup", DocumentLib.newList());

        DBUtilsNoCompany.updateOne(QUserInfo.collectionName, userQuery,
                DocumentLib.new$Set(DocumentLib.newDoc(QUserInfo.company_id, DocumentLib.getID(company)).append
                        ("security", defaultSecurity)));
        return checkMessage.toString();
    }

    /**
     * 重发邮件
     *
     * @param code
     * @return
     */
    @GetMapping(value = "resendRegisterEmail/{code}")
    @ResponseBody
    public String resendRegisterEmail(@PathVariable("code") String code, HttpServletRequest request) {
        CheckMessage result = new CheckMessage();
        Document checker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                DocumentLib.newDoc("code1", code));
        Document user = DBUtilsNoCompany.findByID(QUserInfo.collectionName, DocumentLib.getString(checker, "user_id"));
        registerService.sendUserRegisterEmail(user, checker, request);
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request) {
        String userStr = request.getParameter("user");
        userStr = StringLib.decodeURI(userStr);
        Document userInfo = Document.parse(userStr);

        CheckMessage checkMessage = CheckMessage.newInstance();
        String account = DocumentLib.getString(userInfo, QUserRegisterChecker.username);
        String userName = DocumentLib.getString(userInfo, QUserInfo.name);

        Document existsUser = QUserInfo.findByAccount(account);
        if (existsUser == null || !DocumentLib.getBoolean(existsUser, QUserInfo.validate)) {
            String field = "mobile";
            if (StringLib.contains(account, "@")) {
                field = "email";
            }
            if (existsUser == null) {
                //create new user
                existsUser = new Document();
                existsUser.put(field, account);
                existsUser.put(QUserInfo.name, userName);
                existsUser.put(QUserInfo.password, DigestLib.getPasswordEncoder().encode(DocumentLib.getString(userInfo, QUserInfo.password)));
                existsUser.put(QUserInfo.validate, false);
                existsUser.put("create_date", new Date());
                DBUtilsNoCompany.save(QUserInfo.collectionName, existsUser);
            }

            DBUtilsNoCompany.deleteOne(QUserRegisterChecker.collectionName, DocumentLib.newDoc(field, account));

            Document checker = new Document();
            checker.put(QUserRegisterChecker.code1, RandomUtils.uuid2());
            checker.put(QUserRegisterChecker.code2, RandomUtils.uuid2());
            checker.put(QUserRegisterChecker.user_id, DocumentLib.getID(existsUser));
            checker.put(QUserRegisterChecker.username, userName);
            checker.put(QUserRegisterChecker.email, DocumentLib.getString(existsUser, QUserInfo.email));
            checker.put(QUserRegisterChecker.mobile, DocumentLib.getString(existsUser, QUserInfo.mobile));
            checker.put(QUserRegisterChecker.create_date, new Date());
            checker.put(QUserRegisterChecker.expired, false);

            registerService.sendUserRegisterEmail(existsUser, checker, request);
            DBUtilsCompany.save(QUserRegisterChecker.collectionName, checker);

            checkMessage.addData("code", DocumentLib.getString(checker, QUserRegisterChecker.code1));
            checkMessage.setSuccess(true);
        } else {
            checkMessage.setSuccess(false);
        }
        return checkMessage.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/checkCaptcha")
    public String checkCaptcha(@RequestParam String code, @RequestParam String checkcode) {
        CheckMessage result = new CheckMessage();

        Document checker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                Filters.eq("code1", code));
        if (checker != null) {
            String account = "";
            if (StringLib.isNotEmpty(DocumentLib.getString(checker, QUserRegisterChecker.email))) {
                account = DocumentLib.getString(checker, QUserRegisterChecker.email);
            } else {
                account = DocumentLib.getString(checker, QUserRegisterChecker.mobile);
            }
            if (registerService.checkCode(account, checkcode)) {
                checker.put(QUserRegisterChecker.expired, true);
                DBUtilsCompany.save(QUserRegisterChecker.collectionName, checker);

                Document userInfo = DBUtilsNoCompany.findByID(QUserInfo.collectionName, DocumentLib.getString(checker, QUserRegisterChecker.user_id));
                if (userInfo != null) {
                    userInfo.append(QUserInfo.validate, true);
                    userInfo.append(QUserInfo.create_date, new Date());
//                    DBUtilsNoCompany.updateField(QUserInfo.collectionName,
//                            DocumentLib.newDoc("id", DocumentLib.getID(userInfo)), "validate", true);
                    DBUtilsNoCompany.updateOne(QUserInfo.collectionName,
                            DocumentLib.newDoc("id", DocumentLib.getID(userInfo)),
                            DocumentLib.new$Set(userInfo));
                }
                result.setSuccess(true);
                result.addData(QUserRegisterChecker.code2, DocumentLib.getString(checker, QUserRegisterChecker.code2));
            } else {
                result.setSuccess(false);
            }
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/searchCompany")
    public String searchCompany(@RequestParam String query) {
        CheckMessage cm = new CheckMessage();
        query = StringLib.decodeURI(query);
        Document dbQuery = Document.parse(query);
        List<Document> list = DBUtilsNoCompany.list(QCompany.collectionName,
                dbQuery,
                Projections.include("_id", "name"),
                Sorts.descending("_id"), 10);
        cm.addData("list", list);
        return cm.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/applyCompany")
    public String applyCompany(@RequestParam String code, @RequestParam String id) {
        CheckMessage cm = new CheckMessage();

        Document checker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName,
                DocumentLib.newDoc("code2", code));
        String userId = DocumentLib.getString(checker, QUserRegisterChecker.user_id);
        // 检查是否已申请
        Document companyApplyQuery = DocumentLib.newDoc("company_id", id);
        companyApplyQuery.put("user_id", userId);
        companyApplyQuery.put("state", DocumentLib.newDoc("$exists", false));

        Document apply = DBUtilsNoCompany.find(QCompanyApply.collectionName,
                companyApplyQuery);

//        String mobile = DocumentLib.getString(checker, QUserRegisterChecker.mobile);
//        String email = DocumentLib.getString(checker, QUserRegisterChecker.email);
        Document userInfo = DBUtilsNoCompany.findByID(QUserInfo.collectionName, userId);
        String userName = userInfo == null ? DocumentLib.getString(checker, QUserRegisterChecker.username) : DocumentLib.getString(userInfo, QUserInfo.name);
        // Neptune task 1503
        String mobile = DocumentLib.getString(userInfo, QUserInfo.mobile) == null ? DocumentLib.getString(checker, QUserRegisterChecker.mobile) : DocumentLib.getString(userInfo, QUserInfo.mobile);
        String email = DocumentLib.getString(userInfo, QUserInfo.email) == null ? DocumentLib.getString(checker, QUserRegisterChecker.email) : DocumentLib.getString(userInfo, QUserInfo.email);

        if (apply == null) {
            apply = new Document();
            apply.put(QCompanyApply.user_id, userId);
            apply.put(QCompanyApply.user_name, userName);
            apply.put(QCompanyApply.user_mobile, mobile);
            apply.put(QCompanyApply.user_email, email);
            apply.put(QCompanyApply.company_id, id);
            apply.put("create_date", new Date());
            DBUtilsCompany.save(QCompanyApply.collectionName, apply);
            cm.addMessage("您的申请已提交,等待团队人员审批");

            MessageDto messageDto = new MessageDto();
            messageDto.setSubject("申请加入团队提醒");
            String text = "%s申请加入团队,请处理";
            text = String.format(text, userName);
            messageDto.setContent(text);
            messageDto.addLink("点击处理", "#/company");
            messageDto.setCompany_id(id);
            messageDto.setTeam(true);
            messageDto.setRead(false);
            messageDto.setLevel("alert");
            MessageLib.send(messageDto);
        } else {
            cm.setSuccess(false);
            cm.addMessage("申请失败，您已申请加入该团队，请等待审批");
        }
        return cm.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/joinCompany")
    public String joinCompany(String code, String id) {
        CheckMessage cm = new CheckMessage();
        Document company = DBUtilsNoCompany.findByID(QCompany.collectionName, id);

        Document checker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName, DocumentLib.newDoc("code2", code));
        Document userInfo = DBUtilsNoCompany.findByID(QUserInfo.collectionName, DocumentLib.getString(checker, QUserRegisterChecker.user_id));
        DBUtilsNoCompany.updateOne(QUserInfo.collectionName, Filters.eq("id", DocumentLib.getID(userInfo)), DocumentLib.newDoc("$set", DocumentLib.newDoc("company_id", DocumentLib.getID(company))));

        return cm.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getInviteInfo")
    public String getInviteInfo(String key) {
        CheckMessage cm = new CheckMessage();


        Document query = new Document();
        query.put(QCompanyInvite.hashkey, StringLib.encodeURI(key));

        Document inviteInfo = DBUtilsNoCompany.find(QCompanyInvite.collectionName, query);
        if (inviteInfo == null) {
            cm.setSuccess(false);
        } else {
            cm.addData("info", inviteInfo);
        }

        return cm.toString();
    }


    /**
     * 邮箱邀请确认
     *
     * @param key
     * @param pwd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/acceptInvite")
    public String acceptInvite(String key, String pwd) {
        CheckMessage cm = new CheckMessage();
        cm.setSuccess(false);

        Document inviteInfo = DBUtilsNoCompany.find(QCompanyInvite.collectionName, DocumentLib.newDoc(QCompanyInvite.hashkey, key));
        if (inviteInfo != null) {
            String email = DocumentLib.getString(inviteInfo, QCompanyInvite.email);
            Document userInfo = DBUtilsNoCompany.find(QUserInfo.collectionName, DocumentLib.newDoc(QUserInfo.email, email));
            if (userInfo == null) {
                userInfo = new Document();
                userInfo.put(QUserInfo.email, email);
                userInfo.put(QUserInfo.name, DocumentLib.getString(inviteInfo, QCompanyInvite.to));
                userInfo.put("create_date", new Date());
            }

            // update security
            Document securityObj = DocumentLib.getDocument(inviteInfo, QCompanyInvite.security);
            String password = DocumentLib.getString(securityObj, "password");
            securityObj.remove("password");
            userInfo.put("security", securityObj);

            if (StringLib.isNotEmpty(password)) {
                String encodePassword = QCompany.encodePWD(password);
                userInfo.put("controlDevicePassword", encodePassword);
            }
            userInfo.put(QUserInfo.password, DigestLib.getPasswordEncoder().encode(pwd));
            userInfo.put(QUserInfo.validate, true);
            // set company id
            userInfo.put(QUserInfo.company_id, DocumentLib.getString(inviteInfo, QCompanyInvite.company_id));
            // save userInfo
            DBUtilsCompany.save(QUserInfo.collectionName, userInfo);

            // 删除该邮箱的所有邀请
            DBUtilsNoCompany.deleteOne(QCompanyInvite.collectionName, DocumentLib.newDoc(QCompanyInvite.email, email));

            cm.setSuccess(true);
        }

        return cm.toString();
    }


    /**
     * 邀请码确认
     *
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmInviteCode")
    public String confirmInviteCode(@RequestParam String code) {
        CheckMessage checkMessage = new CheckMessage();
        checkMessage.setSuccess(false);

        // 找到邀请码对应的邀请
        Document info = DBUtilsNoCompany.find(QCompanyInvite.collectionName, DocumentLib.newDoc(QCompanyInvite.code, code));
        if (info != null) {
            String mobile = DocumentLib.getString(info, QCompanyInvite.mobile);
            Document userInfo = QUserInfo.findByAccount(mobile);
            String company_id = DocumentLib.getString(info, QCompanyInvite.company_id);
            if (StringLib.isNotEmpty(company_id)) {
                // update security
                Document securityObj = DocumentLib.getDocument(info, QCompanyInvite.security);
                String password = DocumentLib.getString(securityObj, "password");
                securityObj.remove("password");

                Document updateObject = DocumentLib.newDoc();
                updateObject.put("security", securityObj);

                if (StringLib.isNotEmpty(password)) {
                    String encodePassword = QCompany.encodePWD(password);
                    updateObject.put("controlDevicePassword", encodePassword);
                }

                // set company id
                updateObject.put(QUserInfo.company_id, company_id);
                DBUtilsNoCompany.updateOne(QUserInfo.collectionName, DocumentLib.newDoc("id", DocumentLib.getString(userInfo, "id")), DocumentLib.new$Set(updateObject));

                // 删除该手机的所有邀请
                DBUtilsNoCompany.deleteOne(QCompanyInvite.collectionName, DocumentLib.newDoc(QCompanyInvite.mobile, mobile));

                checkMessage.setSuccess(true);
            }
        }

        return checkMessage.toString();
    }

    @RequestMapping("/checkCompanyApply")
    @ResponseBody
    public String checkCompanyApply(@RequestParam String code, HttpServletRequest request) {
        /*检查是否已申请其它团队*/
        CheckMessage cm = CheckMessage.newInstance();

        Document rUser = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName, DocumentLib.newDoc("code2", code));

        if (rUser != null) {
            String userId = DocumentLib.getString(rUser, QUserRegisterChecker.user_id);

            Bson companyApplyQuery = Filters.and(Filters.eq("user_id", userId), Filters.exists("company_id", true), Filters.exists("state", false));
            Document checker = DBUtilsNoCompany.find(QCompanyApply.collectionName, companyApplyQuery);

            if (checker != null) {
                // Get company info
                Document company = DBUtilsNoCompany.findByID(QCompany.collectionName,
                        DocumentLib.getString(checker, QCompanyApply.company_id));

                cm.addData("applying_company", DocumentLib.getString(company, QCompany.name));
                cm.addData("type", "applying");
                cm.setSuccess(false);
            }
        }
        return cm.toString();
    }

    @RequestMapping("/hideCompanyApply")
    @ResponseBody
    public String hideCompanyApply(@RequestParam String code) {
        /*检查是否已申请其它团队*/
        CheckMessage cm = CheckMessage.newInstance();

        Document rUser = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName, DocumentLib.newDoc("code2", code));

        if (rUser != null) {
            String userId = DocumentLib.getString(rUser, QUserRegisterChecker.user_id);
            Bson companyApplyQuery = Filters.and(Filters.eq("user_id", userId), Filters.exists("company_id", true), Filters.exists("state", false));

            // Set companyApply state to 'joined' (已加入其它团队)
            Document updateField = DocumentLib.newDoc(QCompanyApply.state, "joined").append("update_date", new Date());
            DBUtilsNoCompany.updateOne(QCompanyApply.collectionName, companyApplyQuery, DocumentLib.new$Set(updateField));
        }
        return cm.toString();
    }

    @RequestMapping("/checkCompany")
    @ResponseBody
    public String checkCompany(String username, HttpServletRequest request) {
        CheckMessage cm = CheckMessage.newInstance();

        Document query = new Document();
        if (StringLib.contains(username, "@")) {
            query.put(QUserInfo.email, username);
        } else {
            query.put(QUserInfo.mobile, username);
        }

        // get user
        Document user = QUserInfo.findByAccount(username);
        Document checkerQuery = new Document();
        checkerQuery.put("user_id", DocumentLib.getID(user));
        if (user != null) {
            boolean validate = DocumentLib.getBoolean(user, "validate");

            if (!validate) {
                cm.addData("type", "no_validate");
                cm.setSuccess(false);
            } else if (StringLib.isEmpty(DocumentLib.getString(user, QUserInfo.company_id))) {
                cm.setSuccess(false);
                cm.addData("type", "no_company");
            }
            if (cm.isSuccess() == false) {
                Document rChecker = DBUtilsNoCompany.find(QUserRegisterChecker.collectionName, checkerQuery);
                cm.addData("code2", DocumentLib.getString(rChecker, QUserRegisterChecker.code2));
                cm.addData("code1", DocumentLib.getString(rChecker, QUserRegisterChecker.code1));
                String type = StringLib.toString(cm.getDatas().get("type"));
                if (StringLib.equals(type, "no_validate")) {
                    registerService.sendUserRegisterEmail(user, rChecker, request);
                }
            }
            return cm.toString();
        }

        return cm.toString();
    }

    @RequestMapping(value = "goToLoginPage")
    public void goToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login");
    }
}
