package com.nutcracker.example.demo.jwt;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTest {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        Date now = DateUtil.date();
        Date expiresAt = DateUtil.tomorrow();
        log.info("now={},expiresAt={}", DateUtil.format(now, DatePattern.NORM_DATETIME_MS_FORMAT), DateUtil.format(expiresAt, DatePattern.NORM_DATETIME_MS_FORMAT));
        //生成jwt的代码
        String token = JWT.create()
                //添加载荷
                .withClaim("user", claims)
                //添加过期时间
                .withExpiresAt(expiresAt)
                //指定算法,配置秘钥
                .sign(Algorithm.HMAC256("nutcracker"));
        log.info("token={}", token);
    }

    @Test
    public void testParse() {
        //定义字符串,模拟用户传递过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3NDQyNjYwMjF9.KNDyfjv5zxe_oetmNvRpwIDVJFNM_Oyf5sVG9p9KrUQ";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("nutcracker")).build();
        //验证token,生成一个解析后的JWT对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
        //如果篡改了头部和载荷部分的数据,那么验证失败
        //如果秘钥改了,验证失败
        //token过期
    }
}
