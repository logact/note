package com.cynovan.neptune.oauth.base.config.auth.handler;


import com.cynovan.neptune.oauth.base.config.filter.CorsResponseInterceptor;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric on 2017/1/13.
 */
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLoginFailureHandler() {
    }

    public CustomLoginFailureHandler(String defaultFailureUrl) {
        this.setDefaultFailureUrl("/intro/#/login");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        CorsResponseInterceptor.addCorsResponse(request, response);
        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        if (this.forwardToDestination) {
            this.logger.debug("Forwarding to " + "/intro/#/login");
            request.getRequestDispatcher("/signIn").forward(request, response);
        } else {
            String redirect_uri = request.getParameter("redirect_uri");
            String url = StringLib.join("/login?redirect_uri=", redirect_uri, "&error");
            response.sendRedirect(url);
        }
    }

    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = "/intro/#/login";
    }

    @Override
    protected boolean isUseForward() {
        return this.forwardToDestination;
    }

    @Override
    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }

    @Override
    protected boolean isAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    @Override
    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }
}


