package com.cynovan.neptune.oauth.base.utils;

import com.cynovan.neptune.oauth.base.config.auth.dto.NeptuneTokenDto;
import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.Document;
import org.joda.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtTokenUtils {

    public static final String ACCESS_TOKEN_KEY = "NEPTUNE-ACCESS-TOKEN";
    public static final String REFRESH_TOKEN_KEY = "NEPTUNE-REFRESH-TOKEN";


    private static final String SigningKey = "Neptune-PF-JwT-sIgn_key";

    private static final int ACCESS_TOKEN_EXPIRED = 30;
    private static final int REFRESH_TOKEN_EXPIRED = 7;

    public static void createTokenCookie(HttpServletResponse response, UserToken userToken, String domain) {
        CookieUtil.create(response, ACCESS_TOKEN_KEY, userToken.getAccess_token(), domain);
        CookieUtil.create(response, REFRESH_TOKEN_KEY, userToken.getRefresh_token(), domain);
    }

    public static void clearTokenCookie(HttpServletResponse response, String domain) {
        CookieUtil.clear(response, JwtTokenUtils.ACCESS_TOKEN_KEY, domain);
        CookieUtil.clear(response, JwtTokenUtils.REFRESH_TOKEN_KEY, domain);
    }

    private static String newToken(String userId, int minutes) {
        Date expireDate = LocalDateTime.now().plusMinutes(minutes).toDate();
        JwtBuilder builder = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SigningKey);

        return builder.compact();
    }

    public static String newRefreshToken(String userId) {
        int minutes = StringLib.toInteger(TimeUnit.DAYS.toMinutes(REFRESH_TOKEN_EXPIRED));
        return newToken(userId, minutes);
    }

    public static String newAccessToken(String userId) {
        return newToken(userId, ACCESS_TOKEN_EXPIRED);
    }

    private static String getUserId(String token) {
        if (StringLib.isEmpty(token)) {
            return null;
        }
        try {
            return Jwts.parser().setSigningKey(SigningKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public static UserToken getUserToken(NeptuneTokenDto neptuneTokenDto) {
        String access_token = neptuneTokenDto.getAccess_token();

        String userId = getUserId(access_token);

        /*当access token失效时，用refresh token进行刷新*/
        String refresh_token = neptuneTokenDto.getRefresh_token();
        if (StringLib.isEmpty(userId)) {
            userId = getUserId(refresh_token);
            if (StringLib.isNotEmpty(userId)) {
                Document userInfo =
                        DBUtils.find("userInfo", Filters.eq("_id", userId), Projections.include("name"));
                /*用refresh token 生成 access token的时候再去数据库检查一次*/
                if (userInfo != null) {
                    UserToken userToken = new UserToken();
                    /*由于UserToken失效，重新生成UserToken*/
                    userToken.setId(userId);
                    userToken.setUsername(DocumentLib.getString(userInfo, "name"));
                    userToken.setRefresh_token(refresh_token);
                    /*每次都返回新的access token*/
                    userToken.setAccess_token(newAccessToken(userId));
                    return userToken;
                }
            } else {
                /*当refresh token 和 access token都失效时，直接返回null*/
                return null;
            }
        } else {
            /*access Token 是有效的*/
            UserToken userToken = new UserToken();
            userToken.setId(userId);
            userToken.setRefresh_token(refresh_token);
            userToken.setAccess_token(newAccessToken(userId));
            return userToken;
        }
        return null;
    }
}

