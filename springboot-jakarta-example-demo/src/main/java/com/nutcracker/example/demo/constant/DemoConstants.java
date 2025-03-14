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
    /** cookie */
    public static final String LOGIN_USER_COOKIE_KEY = "sessionUser";
    public static final String CONTEXT_PATH_COOKIE_KEY = "ctx";

    public static final String LOGIN_URL = "/login";

    public static final String LOGOUT_URL = "/logout";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_FAIL = "登录失败";

    public static final String LOGOUT_SUCCESS = "退出成功";

    public static final String LOGIN_MAX_LIMIT = "登录超出最大限制";


    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    public static final String INTRANET_IP = "内网IP";

    public static final String LOCAL_HOST = "127.0.0.1";


    /** 白名单url */
    public static final String[] WHILE_URL_LIST = {"/favicon.ico", "/v3/api-docs/**", "/webjars/**", "/doc.html", "/api/**", "/alive", "/login", "/error/**", "/public/**", "/static/**", "/swagger-ui/**", "/actuator/**"};


}
