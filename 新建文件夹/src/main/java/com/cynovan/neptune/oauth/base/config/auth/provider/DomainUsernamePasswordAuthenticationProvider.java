package com.cynovan.neptune.oauth.base.config.auth.provider;

import com.cynovan.neptune.oauth.base.config.auth.dto.QUserInfo;
import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.cynovan.neptune.oauth.base.utils.DigestLib;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.JwtTokenUtils;
import com.google.common.collect.Lists;
import org.bson.Document;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

public class DomainUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }

        Document userInfo = QUserInfo.findByAccount(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("username not found ");
        }
        String encoderPassword = DocumentLib.getString(userInfo, "password");
        boolean checkPassword = DigestLib.getPasswordEncoder().matches(password, encoderPassword);
        if (checkPassword == false) {
            throw new UsernameNotFoundException("password invalid");
        }
        UserToken userToken = new UserToken();
        String userId = DocumentLib.getID(userInfo);
        userToken.setId(DocumentLib.getID(userInfo));
        userToken.setUsername(DocumentLib.getString(userInfo, "name"));
        userToken.setRefresh_token(JwtTokenUtils.newRefreshToken(userId));
        userToken.setAccess_token(JwtTokenUtils.newAccessToken(userId));

        return new UsernamePasswordAuthenticationToken(userToken, null, Lists.newArrayList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
