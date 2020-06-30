package com.cynovan.neptune.oauth.addons.sign.controller;

import com.cynovan.neptune.oauth.base.utils.DBUtils;
import com.cynovan.neptune.oauth.base.utils.JwtTokenUtils;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Aric.Chen
 * @date 2020/3/17 11:23
 * 自己写的Oauth处理器
 */

@RequestMapping(value = "oauth")
@Controller
public class OauthController {

    @Value("${server.session.cookie.domain}")
    private String cookieDomain;

    @PostMapping(value = "token")
    @ResponseBody
    public String token(@RequestParam String code) {
        Document codeDoc = DBUtils.find("neptune_token", Filters.eq("code", code));
        if (codeDoc != null) {
            return codeDoc.toJson();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JwtTokenUtils.clearTokenCookie(response, cookieDomain);
        String redirect_uri = request.getParameter("redirect_uri");
        if (StringLib.isNotEmpty(redirect_uri)) {
            redirect_uri = StringLib.join(redirect_uri);
            response.sendRedirect(redirect_uri);
        }
    }
}
