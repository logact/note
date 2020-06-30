package com.cynovan.neptune.oauth.base.config.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsResponseInterceptor extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        addCorsResponse(httpServletRequest, httpServletResponse);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public static void addCorsResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String origin = httpServletRequest.getHeader("Origin");
        if (origin == null) {
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        } else {
            httpServletResponse.addHeader("Access-Control-Allow-Origin", origin);
        }

        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.addHeader("Access-Control-Max-Age", "3600");
    }
}
