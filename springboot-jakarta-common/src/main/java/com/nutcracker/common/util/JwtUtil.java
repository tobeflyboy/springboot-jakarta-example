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
    // 创建 JWT Token
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

    // 解析 JWT Token
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

    // 验证 Token 有效性
    public static boolean validateToken(String token, String username, String secret) {
        User user = parseToken(token, secret);
        return user != null && user.getUsername().equals(username);
    }

    //public static String generateSecureKey(int length) {
    //    SecureRandom secureRandom = new SecureRandom();
    //    byte[] keyBytes = new byte[length];
    //    secureRandom.nextBytes(keyBytes);
    //    return Base64.getEncoder().encodeToString(keyBytes);
    //}
    //public static void main(String[] args) {
    //    // 生成 32 字节（256 位）的密钥
    //    String secretKey = generateSecureKey(32);
    //    System.out.println(secretKey);
    //}
}
