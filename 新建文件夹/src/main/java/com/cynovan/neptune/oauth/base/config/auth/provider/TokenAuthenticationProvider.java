package com.cynovan.neptune.oauth.base.config.auth.provider;

import com.cynovan.neptune.oauth.base.config.auth.dto.NeptuneTokenDto;
import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.cynovan.neptune.oauth.base.utils.JwtTokenUtils;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.google.common.collect.Lists;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    public TokenAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        NeptuneTokenDto neptuneToken = (NeptuneTokenDto) authentication.getPrincipal();
        if (neptuneToken == null || StringLib.isEmpty(neptuneToken.getAccess_token()) || StringLib.isEmpty(neptuneToken.getRefresh_token())) {
            throw new BadCredentialsException("Invalid token");
        }

        UserToken userToken = JwtTokenUtils.getUserToken(neptuneToken);

        if (userToken == null) {
            throw new BadCredentialsException("Invalid token or token expired");
        }
        return new UsernamePasswordAuthenticationToken(userToken, null, Lists.newArrayList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
