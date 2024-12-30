package com.nutcracker.example.demo.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 身份验证控制器
 *
 * @author 胡桃夹子
 * @date 2024/12/10 09:50:10
 */
@Slf4j
@Controller
public class AuthController {

    /**
     * jvm设置username，只是为了本地开发调试
     */
    @Value("${username:-}")
    private String username;

    /**
     * jvm设置password，只是为了本地开发调试
     */
    @Value("${password:-}")
    private String password;

    @GetMapping("/login")
    public String login(ModelMap map) {
        map.put("username", username);
        map.put("password", password);
        log.info("login username={},password={}", username, password);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 清除 session 中的用户信息
        request.getSession().invalidate();
        log.info("logout");
        return "redirect:/login";
    }

}
