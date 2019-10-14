package com.maxzuo.web.utils;

import com.maxzuo.web.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;

/**
 * JWT 工具类
 */
public class JwtTokenUtils {

    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    private static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    private static final String TOKEN_TYPE = "JWT";

    /**
     * rememberMe 为 false 的时候过期时间是1个小时
     */
    private static final long EXPIRATION = 60L * 60L;

    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    private static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);

    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;

        String tokenPrefix = Jwts.builder()
                // 标准字段
                .setHeaderParam("typ", TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setSubject(username)
                .setIssuer("dazuo")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                // 自定义字段
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenPrefix;
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    public static String getValueByToken(String token, String key) {
        return (String)getTokenBody(token).get(key);
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
