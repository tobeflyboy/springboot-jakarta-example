package com.nutcracker.common.util;

import com.nutcracker.common.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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

    /**
     * 创建 JWT Token
     *
     * @param user      用户对象
     * @param expiresAt 到期日
     * @param secret    密钥
     * @return {@link String }
     */
    public static String createToken(User user, Date expiresAt, String secret) {
        // 根据密钥字符串生成 Key 对象
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                // 主题设置为账号
                .subject(user.getUsername())
                // 添加载荷
                .claim("userId", user.getUserId())
                .claim("roleId", user.getRoleId())
                // 签发时间
                .issuedAt(new Date())
                // 添加过期时间
                .expiration(expiresAt)
                /// 使用 HMAC256 算法签名，配置秘钥
                .signWith(key)
                .compact();

    }

    /**
     * 解析 JWT Token
     *
     * @param token  token
     * @param secret 密钥
     * @return {@link User }
     */
    public static User parseToken(String token, String secret) {
        try {
            // 根据密钥字符串生成 Key 对象
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = jws.getPayload();
            User user = new User();
            user.setUserId(claims.get("userId", String.class));
            user.setUsername(claims.getSubject());
            user.setRoleId(claims.get("roleId", String.class));
            return user;
        } catch (Exception e) {
            log.error("解析token失败 token={}", token, e);
            return null;
        }
    }

    /**
     * 验证 Token 有效性
     *
     * @param token    token
     * @param username 用户名
     * @param secret   密钥
     * @return boolean
     */
    public static boolean validateToken(String token, String username, String secret) {
        User user = parseToken(token, secret);
        return user != null && user.getUsername().equals(username);
    }

    /**
     * 刷新 JWT Token
     *
     * @param oldToken     旧的 token
     * @param newExpiresAt 新 token 的过期时间
     * @param secret       密钥
     * @return 新的 token（失败返回 null）
     */
    public static String refreshToken(String oldToken, Date newExpiresAt, String secret) {
        // 解析旧 Token 获取用户信息
        final User user = parseToken(oldToken, secret);
        if (user == null) {
            log.warn("Token刷新失败：旧Token无效或已过期");
            return null;
        }

        // 生成新 Token（保留用户信息，更新签发时间和过期时间）
        return createToken(user, newExpiresAt, secret);
    }

}
