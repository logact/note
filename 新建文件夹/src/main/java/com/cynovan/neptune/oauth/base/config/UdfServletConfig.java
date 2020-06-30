package com.cynovan.neptune.oauth.base.config;

import com.cynovan.neptune.oauth.base.config.filter.CorsResponseInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UdfServletConfig extends SpringBootServletInitializer {

    @Bean
    public FilterRegistrationBean crosFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsResponseInterceptor());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("async-supported", "true");
        registration.setName("cors");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
