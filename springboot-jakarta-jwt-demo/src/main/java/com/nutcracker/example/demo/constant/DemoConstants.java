package com.nutcracker.example.demo.constant;

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

    /* session & session key */
    public static final String LOGIN_USER_SESSION_KEY = "sessionUser";

    public static final String LOGIN_URL = "/loginPage";

    public static final String LOGOUT_URL = "/logout";

    /** 菜单最大层级 */
    public static final int PERMISSION_MAX_LEVEL = 3;


    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    public static final String INTRANET_IP = "内网IP";

    public static final String LOCAL_HOST = "127.0.0.1";


    /** 白名单url */
    public static final String[] WHILE_URL_LIST = {"/favicon.ico", "/v3/api-docs/**", "/webjars/**", "/doc.html", "/api/**", "/alive", "/login", "/error/**", "/public/**", "/static/**", "/swagger-ui/**", "/actuator/**"};

    /** 管理员角色代码 */
    public static final String ADMIN_ROLE_CODE = "admin";
    /** 管理员用户名 */
    public static final String ADMIN_USERNAME = "admin";

    /** 令牌值前缀 */
    public static final String TOKEN_VALUE_PREFIX = "Bearer ";
}
