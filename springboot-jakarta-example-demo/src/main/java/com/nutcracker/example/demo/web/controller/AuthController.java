package com.nutcracker.example.demo.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/login")
    public String login() {
        log.info("login");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 清除 session 中的用户信息
        request.getSession().invalidate();
        log.info("logout");
        return "redirect:/login";
    }

    @GetMapping("/auth/permission_list")
    public String permissionList() {
        log.info("/auth/permission_list");
        return "auth/permission_list";
    }

    @GetMapping("/auth/role_list")
    public String roleList() {
        log.info("/auth/role_list");
        return "auth/role_list";
    }

    @GetMapping("/auth/user_list")
    public String userList() {
        log.info("/auth/user_list");
        return "auth/user_list";
    }

}
