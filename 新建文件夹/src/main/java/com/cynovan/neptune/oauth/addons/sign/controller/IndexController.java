package com.cynovan.neptune.oauth.addons.sign.controller;

import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.cynovan.neptune.oauth.base.utils.DBUtils;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.cynovan.neptune.oauth.base.utils.UserUtils;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Aric.Chen
 * @date 2020/3/17 10:51
 */
@Controller
public class IndexController {
    @RequestMapping(value = "")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirect_uri = request.getParameter("redirect_uri");
        UserToken userToken = UserUtils.getUserToken();
        if (userToken != null) {
            Document codeDoc = DBUtils.find("neptune_token",
                    Filters.eq("refresh_token", userToken.getRefresh_token()));
            String code = DocumentLib.getString(codeDoc, "code");
            if (StringLib.isNotEmpty(redirect_uri)) {
                redirect_uri = StringLib.join(redirect_uri, "?code=", code);
                response.sendRedirect(redirect_uri);
            } else {
                /*to user page*/
                response.sendRedirect("user/info");
            }
        } else {
            response.sendRedirect("login?redirect_uri=" + StringLib.encodeURI(redirect_uri));
        }
    }

    @RequestMapping(value = "index")
    public void managerIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirect_uri = request.getParameter("redirect_uri");
        response.sendRedirect(redirect_uri);
    }

}
