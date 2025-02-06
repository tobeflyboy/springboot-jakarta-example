package com.nutcracker.example.demo.web.controller;

import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.util.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 身份验证控制器
 *
 * @author 胡桃夹子
 * @date 2024/12/10 09:50:10
 */
@Slf4j
@Controller
public class AuthController {


    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    public AuthController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

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
    public String roleList(ModelMap model) {
        log.info("/auth/role_list");
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(null, null);
        model.put("page", page);
        return "auth/role_list";
    }

    @PostMapping("/auth/role_list")
    public String roleList(@RequestBody SysRole role, @RequestParam("pageNum") Integer pageNum, ModelMap model) {
        log.info("/auth/role_list");
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(pageNum, role);
        model.put("page", page);
        model.put("role", role);
        return "auth/role_list_page";
    }

    @GetMapping("/auth/user_list")
    public String userList(ModelMap model) {
        log.info("/auth/user_list");
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(null, null);
        log.info("userList {}", JSON.toJSONString(page));
        model.put("page", page);
        model.put("statusMap", SysUserStatusEnum.getStatusMap());
        return "auth/user_list";
    }

    @PostMapping("/auth/user_list")
    public String userList(@RequestBody SysUser user, @RequestParam("pageNum") Integer pageNum, ModelMap model) {
        log.info("/auth/user_list");
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        model.put("page", page);
        model.put("statusMap", SysUserStatusEnum.getStatusMap());
        model.put("user", user);
        return "auth/user_list_page";
    }

}
