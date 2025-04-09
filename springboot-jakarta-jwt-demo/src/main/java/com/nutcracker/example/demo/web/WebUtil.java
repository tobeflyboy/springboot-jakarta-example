package com.nutcracker.example.demo.web;

import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Web选项
 *
 * @author 胡桃夹子
 * @date 2024/12/30 13:32:47
 */
@Slf4j
public class WebUtil {

    private WebUtil() {

    }

    /**
     * 按登录用户设置会话
     *
     * @param request  请求
     * @param response 响应
     * @param sessionUser   用户vo
     */
    @SneakyThrows
    public static void setSessionUser(HttpServletRequest request, HttpServletResponse response, SessionUser sessionUser) {
        // 将 SessionUser 存入 Session
        request.getSession().setAttribute(DemoConstants.LOGIN_USER_SESSION_KEY, sessionUser);
    }

    /**
     * 获取登录用户
     *
     * @param request 请求
     * @return {@link SessionUser }
     */
    public static SessionUser getSessionUser(HttpServletRequest request) {
        return (SessionUser) request.getSession().getAttribute(DemoConstants.LOGIN_USER_SESSION_KEY);
    }

}
