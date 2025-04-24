package com.nutcracker.example.demo.web.rest;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.wrapper.RespWrapper;
import com.nutcracker.example.demo.convert.auth.SysUserConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * auth-api控制器
 *
 * @author 胡桃夹子
 * @date 2025/04/14 11:11:58
 */
@Tag(name = "权限管理", description = "有关资源菜单、角色、用户想着的接口")
@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthApiController {

    private final SysPermissionService sysPermissionService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;

    @Operation(summary = "菜单-树接口", description = "菜单树接口")
    @PostMapping("/api/permission/tree")
    @ResponseBody
    public RespWrapper<List<SysPermission>> permissionTree() {
        log.info("/api/permission/tree");
        List<SysPermission> list = sysPermissionService.findSysPermission();
        RespWrapper<List<SysPermission>> resp = RespWrapper.success(list);
        log.info("/api/permission/tree,{}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "菜单-详情接口", description = "菜单详情接口")
    @PostMapping("/api/permission/{permissionId}")
    @ResponseBody
    public RespWrapper<SysPermission> permission(@PathVariable("permissionId") String permissionId) {
        log.info(" /api/permission/{}", permissionId);
        SysPermission permission = sysPermissionService.getPermission(permissionId);
        RespWrapper<SysPermission> resp = RespWrapper.success(permission);
        log.info(" /api/permission/{},{}", permissionId, JSON.toJSONString(resp));
        return resp;
    }

    @PostMapping("/api/permission/save")
    @ResponseBody
    public RespWrapper<Boolean> permissionSave(@RequestBody SysPermission sysPermission) {
        log.info("/api/permission/save {}", sysPermission);
        RespWrapper<Boolean> response = sysPermissionService.savePermission(sysPermission);
        log.info("/api/permission/save {}, response={}", sysPermission, response);
        return response;
    }

    @PostMapping("/api/permission/delete/{permissionId}")
    @ResponseBody
    public RespWrapper<Boolean> permissionDelete(@PathVariable("permissionId") String permissionId) {
        log.info("/api/permission/delete permissionId={}", permissionId);
        RespWrapper<Boolean> response = sysPermissionService.deletePermission(permissionId);
        log.info("/api/permission/delete permissionId={}, response={}", permissionId, response);
        return response;
    }

    @Operation(summary = "角色-查询所有接口", description = "角色查询所有有效数据接口")
    @PostMapping("/api/role/all-list")
    public RespWrapper<List<SysRole>> roleAllList() {
        log.info("/api/role/all-list");
        RespWrapper<List<SysRole>> resp = sysRoleService.roleList();
        log.info("/api/role/all-list {}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-状态枚举接口", description = "用户状态枚举查询接口")
    @PostMapping("/api/user/status-enum")
    public RespWrapper<Map<Integer, String>> userStatusEnum() {
        Map<Integer, String> statusMap = new LinkedHashMap<>();
        Arrays.stream(SysUserStatusEnum.values()).forEach(e -> statusMap.put(e.getCode(), e.getMsg()));
        RespWrapper<Map<Integer, String>> resp = RespWrapper.success(statusMap);
        log.info("/api/user/status-enum,{}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-列表分页查询接口", description = "用户列表分页查询接口")
    @PostMapping("/api/user/list")
    public RespWrapper<PageInfo<SysUser>> userList(
            @Parameter(name = "pageNum", description = "页码，当前第x页", example = "1")
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestBody(required = false) SysUser user) {
        log.info("/api/user/list pageNum={},{}", pageNum, JSON.toJSONString(user));
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        RespWrapper<PageInfo<SysUser>> resp = RespWrapper.success(page);
        log.info("/api/user/list pageNum={},{},{}", pageNum, JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-新增接口", description = "用户新增接口")
    @PostMapping("/api/user/add")
    public RespWrapper<Boolean> userAdd(@RequestBody SysUser user) {
        log.info("/api/user/add {}", JSON.toJSONString(user));
        SysUserDo userDo = SysUserConvert.INSTANCE.toDo(user);
        SysRoleDo roleDo = ObjectUtil.isEmpty(user) || ObjectUtil.isEmpty(user.getRoleId()) ? null : SysRoleDo.builder().id(user.getRoleId()).build();
        RespWrapper<Boolean> resp = sysUserService.addSysUser(userDo, roleDo);
        log.info("/api/user/add {},{}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-编辑接口", description = "用户编辑接口")
    @PostMapping("/api/user/edit")
    public RespWrapper<Boolean> userEdit(@RequestBody SysUser user) {
        log.info("/api/user/edit {}", JSON.toJSONString(user));
        RespWrapper<Boolean> resp = sysUserService.editUser(user);
        log.info("/api/user/edit {}, resp={}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-删除接口", description = "用户删除接口")
    @PostMapping("/api/user/delete/{userId}")
    public RespWrapper<Boolean> deleteUser(@PathVariable("userId") String userId) {
        log.info("/api/delete/user/{}", userId);
        RespWrapper<Boolean> resp = sysUserService.deleteUser(userId);
        log.info("/api/delete/user/{}, resp={}", userId, JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户-重置密码接口", description = "用户重置密码接口")
    @PostMapping("/api/user/reset-pwd")
    @ResponseBody
    public RespWrapper<Boolean> resetPwd(@RequestBody SysUser user) {
        log.info("/api/user/reset-pwd {}", JSON.toJSONString(user));
        RespWrapper<Boolean> resp = sysUserService.resetPwd(user);
        log.info("/api/user/reset-pwd {}, response={}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }


}
