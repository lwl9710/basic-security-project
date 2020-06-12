package com.basic.main.utils;

import com.basic.main.dto.RSA;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 * Author: 南天
 * Date: 2020-06-12 15:34
 * Content: JWT工具
 */

public class JwtUtil {
    //RSA密钥
    private static final RSA RSA_KEY;
    //token时效(单位：分)
    private static final long EXPIRATION_TIME = 120;
    static {
        ClassLoader classLoader = JwtUtil.class.getClassLoader();
        RSA_KEY = new RSA(classLoader.getResourceAsStream("keys/public.key"), classLoader.getResourceAsStream("keys/private.key"));
    }

    private JwtUtil() {

    }
    //创建token
    public static String createToken(Map<String, Object> payload) {
        JwtBuilder jwtBuilder = Jwts.builder();
        long currentTimeMillis = System.currentTimeMillis();
        jwtBuilder.signWith(SignatureAlgorithm.RS256, RSA_KEY.getPrivateKey());
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        jwtBuilder.setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME * 60000));
        jwtBuilder.setClaims(payload);
        return jwtBuilder.compact();
    }
    //解析token
    public static Map<String, Object> parserToken(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(RSA_KEY.getPublicKey());
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody();
    }
    //解析token指定key
    public static Object parserTokenToValue(String token, String key) {
        Map<String, Object> payload = parserToken(token);
        return payload.get(key);
    }
}
