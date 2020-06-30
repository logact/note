package com.cynovan.neptune.oauth.base.config;

import com.cynovan.neptune.oauth.base.config.auth.AuthenticationFilter;
import com.cynovan.neptune.oauth.base.config.auth.handler.CustomLoginFailureHandler;
import com.cynovan.neptune.oauth.base.config.auth.handler.CustomLoginSuccessHandler;
import com.cynovan.neptune.oauth.base.config.auth.provider.DomainUsernamePasswordAuthenticationProvider;
import com.cynovan.neptune.oauth.base.config.auth.provider.TokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(50)
class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(domainUsernamePasswordAuthenticationProvider())
                .authenticationProvider(tokenAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider domainUsernamePasswordAuthenticationProvider() {
        return new DomainUsernamePasswordAuthenticationProvider();
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/resource/**", "/login", "/index", "/intro", "/register/**", "/retrievePwd/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http.httpBasic().disable();

        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/initialize/**", "/actuator/**", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login")
                .failureHandler(newCustomLoginFailureHandler())
                .loginProcessingUrl("/authenticate")
                .successForwardUrl("/user")
                .successHandler(newCustomLoginSuccessHandler())
                .and()
                .rememberMe()
                .key("rememberme")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthenticationFilter(authenticationManager()),
                        BasicAuthenticationFilter.class);
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public CustomLoginFailureHandler newCustomLoginFailureHandler() throws Exception {
        return new CustomLoginFailureHandler();
    }

    public CustomLoginSuccessHandler newCustomLoginSuccessHandler() throws Exception {
        return new CustomLoginSuccessHandler();
    }
}