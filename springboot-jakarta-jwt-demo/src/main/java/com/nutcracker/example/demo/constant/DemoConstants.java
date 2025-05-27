package com.nutcracker.example.demo.constant;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

/**
 * 常量类
 *
 * @author 胡桃夹子
 * @date 2021-11-17 17:56
 */
public class DemoConstants {

    private DemoConstants() {
    }

    public static final String BR = "\n";

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;

    /** 管理员角色代码 */
    public static final String ADMIN_ROLE_CODE = "admin";
    /** 管理员用户名 */
    public static final String ADMIN_USERNAME = "admin";

    /** 令牌名称 */
    public static final String TOKEN = "Token";

    /** 令牌值前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    // 白名单路径（无需认证）
    public static final String[] WHITE_LIST = {
            "/public/**",
            "/static/**",
            "/favicon.ico",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/doc.html",
            "/rest/actuator/**",
            "/actuator/**",
            "/alive",
            "/api/login",
            "/error/**"
    };

    /**
     * 是不是白名单
     *
     * @param uri uri
     * @return boolean(true=是;false=不是;)
     */
    public static boolean isWhitelisted(String uri) {
        return Arrays.stream(WHITE_LIST)
                .anyMatch(pattern ->
                        new AntPathMatcher().match(pattern, uri)
                );
    }
}
