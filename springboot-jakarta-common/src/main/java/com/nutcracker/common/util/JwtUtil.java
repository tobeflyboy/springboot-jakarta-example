package com.nutcracker.common.util;

import cn.hutool.core.map.MapUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.nutcracker.common.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT util
 *
 * @author wangxin4
 * @date 2025/04/27 17:17:59
 */
@Slf4j
public class JwtUtil {

    private JwtUtil() {

    }

    public static Map<String, Object> getClaimsMap(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("realName", user.getRealName());
        claims.put("roleId", user.getRoleId());
        claims.put("roleCode", user.getRoleCode());
        claims.put("roleName", user.getRoleName());
        return claims;
    }

    public static User getUser(Map<String, Object> getClaimsMap) {
        return User.builder()
                .userId(MapUtil.getStr(getClaimsMap, "userId"))
                .username(MapUtil.getStr(getClaimsMap, "username"))
                .realName(MapUtil.getStr(getClaimsMap, "realName"))
                .roleId(MapUtil.getStr(getClaimsMap, "roleId"))
                .roleCode(MapUtil.getStr(getClaimsMap, "roleCode"))
                .roleName(MapUtil.getStr(getClaimsMap, "roleName"))
                .build();
    }

    public static String createToken(User user, Date expiresAt, String secret) {
        return JWT.create()
                //添加载荷
                .withClaim("user", getClaimsMap(user))
                //添加过期时间
                .withExpiresAt(expiresAt)
                //指定算法,配置秘钥
                .sign(Algorithm.HMAC256(secret));
    }

    public static User parseToken(String token, String secret) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        //验证token,生成一个解析后的JWT对象
        DecodedJWT decodedJwt = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJwt.getClaims();
        if (MapUtil.isNotEmpty(claims)) {
            return getUser(claims.get("user").asMap());
        }
        return null;
    }
}
