package com.nutcracker.example.demo.web;

import com.nutcracker.common.domain.User;
import com.nutcracker.common.util.JwtUtil;
import com.nutcracker.example.demo.constant.DemoConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

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
    public static void setSessionUser(HttpServletRequest request, HttpServletResponse response, User sessionUser) {
        // 将 SessionUser 存入 Session
        request.getSession().setAttribute(DemoConstants.LOGIN_USER_SESSION_KEY, sessionUser);
    }

    /**
     * 获取登录用户
     *
     * @param request 请求
     * @return {@link User }
     */
    public static User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(DemoConstants.LOGIN_USER_SESSION_KEY);
    }

    public static User getUser(String secret) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        log.info("token:{}", token);
        return JwtUtil.parseToken(token, secret);
    }

}
