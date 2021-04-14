package com.yiqiandewo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.*;

public class JWTUtils {

    /**
     * 过期时间为一天
     */
    private static final long EXPIRE_TIME = 24*60*60*1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "joijsdfjlsjfljfljl5135313135";

    /**
     * 生成签名,15分钟后过期
     * @param username
     * @return
     */
    public static String createToken(String username) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        //设置头信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        String token = JWT.create().withHeader(header).withClaim("loginName", username)
                .withExpiresAt(date).sign(algorithm);  //withHeader是封装Token的header //withClaim是传入payload  //sign 是signature

        return token;
    }

    public static DecodedJWT verifyToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        //DecodedJWT 将jwt解密 将xxx.yyy.zzz 分开
        return verifier.verify(token);
    }

}
