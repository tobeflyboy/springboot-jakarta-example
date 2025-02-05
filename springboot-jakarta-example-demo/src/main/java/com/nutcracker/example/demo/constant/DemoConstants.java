package com.nutcracker.example.demo.constant;

/**
 * 常量类
 *
 * @author 胡桃夹子
 * @date 2021-11-17 17:56
 */
public class DemoConstants {

    private DemoConstants(){

    }

    public static final String BR = "\n";

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;

    /* 状态,1=有效，0=失效 */
    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;

    /* session & session key */
    public static final String PERMISSION_SESSION = "permission_session";
    public static final String LOGIN_USER_SESSION_KEY = "sessionUser";

    // url & roleName
    public static final String ROLE_CODE = "role_code";
    public static final String PERMISSION_URL = "permission_url";

    public static final String ROLE_BOSS_CODE = "boss_role";
    // 管理员
    public static final String ROLE_MANAGER_CODE = "manager_role";
    // 普通用户
    public static final String COMMON_ROLE_CODE = "common_role";

    public static final String ROLE_BOSS_NAME = "总经理";
    public static final String ROLE_MANAGER_NAME = "管理员";
    public static final String ROLE_COMMON_NAME = "普通用户";


    public static final String LOGIN_URL = "/login";

    public static final String LOGOUT_URL = "/logout";

    public static final Integer INT_PAGE_ERROR = 500;

    public static final Integer INT_PAGE_NOT_FOUND = 404;

    public static final String STRING_PAGE_ERROR = "500";

    public static final String STRING_PAGE_NOT_FOUND = "404";

    public static final String REQUEST_MODE_POST = "POST";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_FAIL = "登录失败";

    public static final String LOGOUT_SUCCESS = "退出成功";

    public static final String LOGIN_MAX_LIMIT = "登录超出最大限制";

    /**
     * 五分钟需要之内允许修改密码错误三次
     */
    public static final Long PASSWORD_UPDATE_MINUTE = 300L;

    public static final String PASSWORD_UPDATE = "PASSWORD_UPDATE";

    public static final Integer ACCESS_AUTH_FILTER_ORDER = 10;

    public static final String MENU_ICON_PREFIX = "layui-icon ";


    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    public static final String INTRANET_IP = "内网IP";

    public static final String LOCAL_HOST = "127.0.0.1";

    /**
     * 重置密码  123456
     */
    public static final String CZMM = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";


    /** 白名单url */
    public static final String[] WHILE_URL_LIST = {"/favicon.ico","/v3/api-docs/**", "/webjars/**", "/doc.html", "/api/**", "/alive", "/login", "/error/**", "/public/**", "/static/**"};


}
