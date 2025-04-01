package com.nutcracker.example.demo.web.controller;

import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.ApiResponse;
import com.nutcracker.example.demo.entity.domain.auth.SaveRolePermission;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.util.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 身份验证控制器
 *
 * @author 胡桃夹子
 * @date 2024/12/10 09:50:10
 */
@RequiredArgsConstructor
@Slf4j
@Controller
public class AuthController {

    private final SysPermissionService sysPermissionService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;

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

    @GetMapping("/auth/permission")
    public String permissionList() {
        log.info("/auth/permission");
        return "auth/permission";
    }

    @GetMapping("/auth/permission/tree")
    @ResponseBody
    public List<SysPermission> permissionTree() {
        log.info("/auth/permission/tree");
        return sysPermissionService.findSysPermission();
    }

    @PostMapping("/auth/permission/save")
    @ResponseBody
    public ApiResponse<Boolean> permissionSave(@RequestBody SysPermission sysPermission) {
        log.info("/auth/permission/save {}", sysPermission);
        ApiResponse<Boolean> response = sysPermissionService.savePermission(sysPermission);
        log.info("/auth/permission/save {}, response={}", sysPermission, response);
        return response;
    }

    @PostMapping("/auth/permission/delete/{permissionId}")
    @ResponseBody
    public ApiResponse<Boolean> permissionDelete(@PathVariable("permissionId") String permissionId) {
        log.info("/auth/permission/delete permissionId={}", permissionId);
        ApiResponse<Boolean> response = sysPermissionService.deletePermission(permissionId);
        log.info("/auth/permission/delete permissionId={}, response={}", permissionId, response);
        return response;
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

    @PostMapping("/auth/add/role")
    @ResponseBody
    public ApiResponse<Boolean> addRole(@RequestBody SysRole role) {
        log.info("/auth/add/save {}", role);
        ApiResponse<Boolean> response = sysRoleService.addSysRole(role);
        log.info("/auth/add/save {}, response={}", role, response);
        return response;
    }

    @PostMapping("/auth/edit/role")
    @ResponseBody
    public ApiResponse<Boolean> editRole(@RequestBody SysRole role) {
        log.info("/auth/edit/save {}", role);
        ApiResponse<Boolean> response = sysRoleService.editRole(role);
        log.info("/auth/edit/save {}, response={}", role, response);
        return response;
    }

    @GetMapping("/auth/role_permission/{roleId}")
    @ResponseBody
    public List<SysPermission> rolePermission(@PathVariable("roleId") String roleId) {
        log.info("/auth/role_permission/{}", roleId);
        List<SysPermission> list = sysPermissionService.getSysPermissionByRoleId(roleId);
        log.info("/auth/role_permission/{},list={}", roleId, JSON.toJSONString(list));
        return list;
    }

    @PostMapping("/auth/role_permission/save")
    @ResponseBody
    public ApiResponse<Boolean> rolePermissionSave(@RequestBody SaveRolePermission save) {
        log.info("/auth/role_permission/save {}", save);
        ApiResponse<Boolean> response = sysRoleService.saveRolePermission(save);
        log.info("/auth/role_permission/save {}, response={}", save, response);
        return response;
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

    @PostMapping("/auth/create_user")
    public String createUser(@RequestBody SysUser user, ModelMap model) {
        log.info("/auth/create_user user={}", user);
        //PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        //model.put("page", page);
        //model.put("statusMap", SysUserStatusEnum.getStatusMap());
        //model.put("user", user);
        return "auth/user_list_page";
    }

}
