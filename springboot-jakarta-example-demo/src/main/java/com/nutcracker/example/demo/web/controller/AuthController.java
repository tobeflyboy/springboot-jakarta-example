package com.nutcracker.example.demo.web.controller;

import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysPermissionHideEnum;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
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

    private final SysPermissionService sysPermissionService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;

    public AuthController(SysPermissionService sysPermissionService,
                          SysRoleService sysRoleService,
                          SysUserService sysUserService) {
        this.sysPermissionService = sysPermissionService;
        this.sysRoleService = sysRoleService;
        this.sysUserService = sysUserService;
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
    public String permissionList(ModelMap model) {
        log.info("/auth/permission_list");
        PageInfo<SysPermission> page = sysPermissionService.findSysPermissionByPage(null, null);
        model.put("page", page);
        model.put("hideMap", SysPermissionHideEnum.getHideMap());
        return "auth/permission_list";
    }

    @PostMapping("/auth/permission_list")
    public String permissionList(@RequestParam("pageNum") Integer pageNum, @RequestBody SysPermission permission, ModelMap model) {
        log.info("/auth/permission_list pageNum={},{}", pageNum, permission);
        PageInfo<SysPermission> page = sysPermissionService.findSysPermissionByPage(pageNum, permission);
        model.put("page", page);
        model.put("hideMap", SysPermissionHideEnum.getHideMap());
        model.put("permission", permission);
        return "auth/permission_list_page";
    }

    @GetMapping("/auth/role_list")
    public String roleList(ModelMap model) {
        log.info("/auth/role_list");
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(null, null);
        model.put("page", page);
        return "auth/role_list";
    }

    @PostMapping("/auth/role_list")
    public String roleList(@RequestParam("pageNum") Integer pageNum, @RequestBody SysRole role, ModelMap model) {
        log.info("/auth/role_list pageNum={},{}", pageNum, role);
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
    public String userList(@RequestParam("pageNum") Integer pageNum, @RequestBody SysUser user, ModelMap model) {
        log.info("/auth/user_list pageNum={},{}", pageNum, user);
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        model.put("page", page);
        model.put("statusMap", SysUserStatusEnum.getStatusMap());
        model.put("user", user);
        return "auth/user_list_page";
    }

}
