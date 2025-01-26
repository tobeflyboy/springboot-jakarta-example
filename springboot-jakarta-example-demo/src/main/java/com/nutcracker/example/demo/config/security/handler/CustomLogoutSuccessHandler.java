package com.nutcracker.example.demo.config.security.handler;

import com.nutcracker.example.demo.constant.DemoConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomLogoutSuccessHandler
 *
 * @author 胡桃夹子
 * @date 2022/12/23 08:27
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Value("${server.servlet.context-path}")
    private String path;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String name = token.getName();
        log.info("onLogoutSuccess 用户：{} 退出成功", name);
        //保存日志
        response.sendRedirect(path == null ? DemoConstants.LOGIN_URL : path + DemoConstants.LOGIN_URL);
    }
}
