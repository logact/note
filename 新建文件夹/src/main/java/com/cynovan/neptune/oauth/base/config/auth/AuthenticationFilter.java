package com.cynovan.neptune.oauth.base.config.auth;

import com.cynovan.neptune.oauth.base.config.auth.dto.NeptuneTokenDto;
import com.cynovan.neptune.oauth.base.utils.CookieUtil;
import com.cynovan.neptune.oauth.base.utils.JwtTokenUtils;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        /*首先判断AccessToken的过期时间*/
        String access_token = CookieUtil.getValue(httpRequest, JwtTokenUtils.ACCESS_TOKEN_KEY);
        String refresh_token = CookieUtil.getValue(httpRequest, JwtTokenUtils.REFRESH_TOKEN_KEY);

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
        if (postToAuthenticate(httpRequest, resourcePath)) {
            String username = httpRequest.getParameter("username");
            String password = httpRequest.getParameter("password");
            processUsernamePasswordAuthentication(httpResponse, username, password);
            return;
        } else if (StringLib.isNotEmpty(access_token) || StringLib.isNotEmpty(refresh_token)) {
            NeptuneTokenDto userToken = new NeptuneTokenDto(access_token, refresh_token);
            processTokenAuthentication(userToken);
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return StringLib.indexOf(resourcePath, "/authenticate") != -1 && httpRequest.getMethod().equals("POST");
    }

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, String username, String password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(String username, String password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private void processTokenAuthentication(NeptuneTokenDto userToken) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(userToken);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(NeptuneTokenDto userToken) {
        PreAuthenticatedAuthenticationToken requestAuthentication =
                new PreAuthenticatedAuthenticationToken(userToken, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        return responseAuthentication;
    }
}
