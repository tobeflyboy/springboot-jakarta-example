package com.nutcracker.example.demo.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.nutcracker.common.util.JSON;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.convert.auth.SysUserConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SaveRolePermission;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.common.wrapper.RespWrapper;
import io.swagger.v3.oas.annotations.Hidden;
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
@Hidden
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
    public RespWrapper<Boolean> permissionSave(@RequestBody SysPermission sysPermission) {
        log.info("/auth/permission/save {}", sysPermission);
        RespWrapper<Boolean> response = sysPermissionService.savePermission(sysPermission);
        log.info("/auth/permission/save {}, response={}", sysPermission, response);
        return response;
    }

    @PostMapping("/auth/permission/delete/{permissionId}")
    @ResponseBody
    public RespWrapper<Boolean> permissionDelete(@PathVariable("permissionId") String permissionId) {
        log.info("/auth/permission/delete permissionId={}", permissionId);
        RespWrapper<Boolean> response = sysPermissionService.deletePermission(permissionId);
        log.info("/auth/permission/delete permissionId={}, response={}", permissionId, response);
        return response;
    }

    @GetMapping("/auth/role_list")
    public String roleList(ModelMap model) {
        log.info("/auth/role_list");
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(null, null);
        model.put("page", page);
        model.put("admin_role_code", DemoConstants.ADMIN_ROLE_CODE);
        return "auth/role_list";
    }

    @PostMapping("/auth/role_list")
    public String roleList(@RequestParam("pageNum") Integer pageNum, @RequestBody SysRole role, ModelMap model) {
        log.info("/auth/role_list pageNum={},{}", pageNum, role);
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(pageNum, role);
        model.put("page", page);
        model.put("role", role);
        model.put("admin_role_code", DemoConstants.ADMIN_ROLE_CODE);
        return "auth/role_list_page";
    }

    @PostMapping("/auth/add/role")
    @ResponseBody
    public RespWrapper<Boolean> addRole(@RequestBody SysRole role) {
        log.info("/auth/add/save {}", role);
        RespWrapper<Boolean> response = sysRoleService.addRole(role);
        log.info("/auth/add/save {}, response={}", role, response);
        return response;
    }

    @PostMapping("/auth/edit/role")
    @ResponseBody
    public RespWrapper<Boolean> editRole(@RequestBody SysRole role) {
        log.info("/auth/edit/save {}", role);
        RespWrapper<Boolean> response = sysRoleService.editRole(role);
        log.info("/auth/edit/save {}, response={}", role, response);
        return response;
    }

    @PostMapping("/auth/role/list")
    @ResponseBody
    public RespWrapper<List<SysRole>> roleList() {
        log.info("/auth/role/list");
        RespWrapper<List<SysRole>> response = sysRoleService.roleList();
        log.info("/auth/role/list, response={}", response);
        return response;
    }

    @PostMapping("/auth/role/delete/{roleId}")
    @ResponseBody
    public RespWrapper<Boolean> deleteRole(@PathVariable("roleId") String roleId) {
        log.info("/auth/role/delete/{}", roleId);
        RespWrapper<Boolean> response = sysRoleService.deleteRole(roleId);
        log.info("/auth/role/delete/{}, response={}", roleId, response);
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
    public RespWrapper<Boolean> rolePermissionSave(@RequestBody SaveRolePermission save) {
        log.info(" /auth/role_permission/save {}", save);
        RespWrapper<Boolean> response = sysRoleService.saveRolePermission(save);
        log.info(" /auth/role_permission/save {}, response={}", save, response);
        return response;
    }

    @GetMapping("/auth/user_list")
    public String userList(ModelMap model) {
        log.info("/auth/user_list");
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(null, null);
        log.info("userList {}", JSON.toJSONString(page));
        model.put("page", page);
        model.put("statusMap", SysUserStatusEnum.getStatusMap());
        model.put("admin_username", DemoConstants.ADMIN_USERNAME);
        return "auth/user_list";
    }

    @PostMapping("/auth/user_list")
    public String userList(@RequestParam("pageNum") Integer pageNum, @RequestBody SysUser user, ModelMap model) {
        log.info("/auth/user_list pageNum={},{}", pageNum, user);
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        model.put("page", page);
        model.put("statusMap", SysUserStatusEnum.getStatusMap());
        model.put("user", user);
        model.put("admin_username", DemoConstants.ADMIN_USERNAME);
        return "auth/user_list_page";
    }

    @PostMapping("/auth/user/add")
    @ResponseBody
    public RespWrapper<Boolean> userAdd(@RequestBody SysUser user) {
        log.info("/auth/user/add {}", user);
        SysUserDo userDo = SysUserConvert.INSTANCE.toDo(user);
        SysRoleDo roleDo = ObjectUtil.isEmpty(user) || ObjectUtil.isEmpty(user.getRoleId()) ? null : SysRoleDo.builder().id(user.getRoleId()).build();
        RespWrapper<Boolean> response = sysUserService.addSysUser(userDo, roleDo);
        log.info("/auth/user/add {}, response={}", user, response);
        return response;
    }

    @PostMapping("/auth/user/delete/{userId}")
    @ResponseBody
    public RespWrapper<Boolean> deleteUser(@PathVariable("userId") String userId) {
        log.info("/auth/delete/user/{}", userId);
        RespWrapper<Boolean> response = sysUserService.deleteUser(userId);
        log.info("/auth/delete/user/{}, response={}", userId, response);
        return response;
    }

    @PostMapping("/auth/user/edit")
    @ResponseBody
    public RespWrapper<Boolean> userEdit(@RequestBody SysUser user) {
        log.info(" /auth/user/edit {}", user);
        RespWrapper<Boolean> response = sysUserService.editUser(user);
        log.info(" /auth/user/edit {}, response={}", user, response);
        return response;
    }

    @PostMapping("/auth/user/reset-pwd")
    @ResponseBody
    public RespWrapper<Boolean> resetPwd(@RequestBody SysUser user) {
        log.info(" /auth/user/reset-pwd {}", user);
        RespWrapper<Boolean> response = sysUserService.resetPwd(user);
        log.info(" /auth/user/reset-pwd {}, response={}", user, response);
        return response;
    }

}
