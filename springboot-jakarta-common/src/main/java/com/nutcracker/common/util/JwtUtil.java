package com.nutcracker.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.nutcracker.common.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT util
 *
 * @author 胡桃夹子
 * @date 2025/04/27 17:17:59
 */
@Slf4j
public class JwtUtil {

    private JwtUtil() {

    }

    public static String createToken(User user, Date expiresAt, String secret) {
        return JWT.create()
                // 主题设置为账号
                .withSubject(user.getUsername())
                // 添加载荷
                .withClaim("userId", user.getUserId())
                .withClaim("realName", user.getRealName())
                .withClaim("roleId", user.getRoleId())
                .withClaim("roleCode", user.getRoleCode())
                .withClaim("roleName", user.getRoleName())
                // 签发时间
                .withIssuedAt(new Date())
                // 添加过期时间
                .withExpiresAt(expiresAt)
                // 使用 HMAC256 算法签名,配置秘钥
                .sign(Algorithm.HMAC256(secret));
    }

    public static User parseToken(String token, String secret) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            //验证token,生成一个解析后的JWT对象
            DecodedJWT jwt = jwtVerifier.verify(token);
            User user = new User();
            user.setUserId(jwt.getClaim("userId").asString());
            user.setUsername(jwt.getSubject());
            user.setRealName(jwt.getClaim("realName").asString());
            user.setRoleId(jwt.getClaim("roleId").asString());
            user.setRoleCode(jwt.getClaim("roleCode").asString());
            user.setRoleName(jwt.getClaim("roleName").asString());
            return user;
        } catch (Exception e) {
            log.error("解析token失败 token={}", token, e);
            return null;
        }
    }

    /**
     * 验证 Token 有效性
     */
    public static boolean validateToken(String token, String username, String secret) {
        User user = parseToken(token, secret);
        return user != null && user.getUsername().equals(username);
    }
}
