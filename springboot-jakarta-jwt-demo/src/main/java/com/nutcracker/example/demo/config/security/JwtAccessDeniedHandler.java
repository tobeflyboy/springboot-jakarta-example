package com.nutcracker.example.demo.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 权限不足处理器（有 Token，但无权限访问）
 *
 * @author 胡桃夹子
 * @date 2025/04/30
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("Processing request for URI: {}", request.getRequestURI());
        // 设置响应状态码为 403 禁止访问
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        String json = "{\"code\":403,\"flag\":false,\"message\":\"权限不足，禁止访问\"}";
        response.getWriter().write(json);
    }
}