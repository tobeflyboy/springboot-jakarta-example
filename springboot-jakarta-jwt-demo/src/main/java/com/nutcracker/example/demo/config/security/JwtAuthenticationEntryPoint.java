package com.nutcracker.example.demo.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 未登录处理器（Token 不存在、过期、无效等）
 *
 * @author 胡桃夹子
 * @date 2025/04/30
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Processing request for URI: {}", request.getRequestURI());
        // 设置响应状态码为 401 未授权
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String json = "{\"code\":401,\"flag\":false,\"message\":\"未登录或登录已过期\"}";
        response.getWriter().write(json);
    }
}