package com.cynovan.neptune.oauth.base.config.auth.handler;

import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.cynovan.neptune.oauth.base.context.SpringContext;
import com.cynovan.neptune.oauth.base.utils.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Eric on 2017/1/13.
 */
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public CustomLoginSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        CorsResponseInterceptor.addCorsResponse(response);
        UserToken userToken = (UserToken) authentication.getPrincipal();
        Environment environment = SpringContext.getBean(Environment.class);
        /*存储Token的信息到数据库，返回给其他系统以Code，因为不能跨域名写token*/

        String code = createNeptuneTokenCode(userToken);
        String domain = StringLib.toString(environment.getProperty("server.session.cookie.domain"));
        if (StringLib.isNotEmpty(domain)) {
            String domainArr[] = StringLib.split(domain, ",");
            Arrays.stream(domainArr).forEach(item -> {
                if (StringLib.isNotEmpty(item)) {
                    JwtTokenUtils.createTokenCookie(response, userToken, item);
                }
            });
        }

        // 
        if (request.getParameter("app") != null) {
            ObjectNode result = JsonLib.createObjNode().put("authCode", code);
            response.getWriter().write(result.toString());
            return;
        }

        String redirect_uri = request.getParameter("redirect_uri");
        if (StringLib.isNotEmpty(redirect_uri)) {
            redirect_uri = StringLib.join(redirect_uri, "?code=", code);
            response.sendRedirect(redirect_uri);
        } else {
            response.sendRedirect("user/info");
        }
    }

    private String createNeptuneTokenCode(UserToken userToken) {
        Document codeToken = new Document();
        codeToken.put("access_token", userToken.getAccess_token());
        codeToken.put("refresh_token", userToken.getRefresh_token());
        String code = StringLib.join(userToken.getAccess_token(), "@", userToken.getRefresh_token());
        code = DigestLib.md5Hex(code);
        codeToken.put("code", code);
        codeToken.put("create_date", new Date());
        codeToken.put("user_id", userToken.getId());
        DBUtils.save("neptune_token", codeToken);
        return code;
    }
}


