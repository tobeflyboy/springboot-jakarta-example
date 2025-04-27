package com.nutcracker.example.demo.web.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.wrapper.RespWrapper;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @Operation(summary = "【菜单】菜单树接口", description = "菜单树接口")
    @PostMapping("/api/permission/tree")
    public RespWrapper<List<SysPermission>> permissionTree() {
        log.info("/api/permission/tree");
        List<SysPermission> list = sysPermissionService.findSysPermission();
        RespWrapper<List<SysPermission>> resp = RespWrapper.success(list);
        log.info("/api/permission/tree,{}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【菜单】菜单详情接口", description = "菜单详情接口")
    @PostMapping("/api/permission/{permissionId}")
    public RespWrapper<SysPermission> permission(@PathVariable("permissionId") String permissionId) {
        log.info(" /api/permission/{}", permissionId);
        SysPermission permission = sysPermissionService.getPermission(permissionId);
        RespWrapper<SysPermission> resp = RespWrapper.success(permission);
        log.info(" /api/permission/{},{}", permissionId, JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【菜单】菜单新增或菜单更新", description = "菜单新增或菜单更新接口")
    @PostMapping("/api/permission/save")
    public RespWrapper<Boolean> permissionSave(@RequestBody SysPermission sysPermission) {
        log.info("/api/permission/save {}", sysPermission);
        RespWrapper<Boolean> response = sysPermissionService.savePermission(sysPermission);
        log.info("/api/permission/save {}, response={}", sysPermission, response);
        return response;
    }

    @Operation(summary = "【菜单】菜单删除接口", description = "菜单删除接口")
    @PostMapping("/api/permission/delete/{permissionId}")
    public RespWrapper<Boolean> permissionDelete(@PathVariable("permissionId") String permissionId) {
        log.info("/api/permission/delete permissionId={}", permissionId);
        RespWrapper<Boolean> response = sysPermissionService.deletePermission(permissionId);
        log.info("/api/permission/delete permissionId={}, response={}", permissionId, response);
        return response;
    }

    @Operation(summary = "【角色】查询所有角色接口", description = "角色查询所有有效数据接口")
    @PostMapping("/api/role/all-list")
    public RespWrapper<List<SysRole>> roleAllList() {
        log.info("/api/role/all-list");
        RespWrapper<List<SysRole>> resp = sysRoleService.roleList();
        log.info("/api/role/all-list {}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【角色】角色列表分页查询接口", description = "角色列表分页查询接口")
    @PostMapping("/api/role/list")
    public RespWrapper<PageInfo<SysRole>> roleList(
            @Parameter(name = "pageNum", description = "页码，当前第x页", example = "1")
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestBody SysRole role) {
        log.info("/api/role/list pageNum={},role={}", pageNum, JSON.toJSONString(role));
        PageInfo<SysRole> page = sysRoleService.findSysRoleByPage(pageNum, role);
        RespWrapper<PageInfo<SysRole>> resp = RespWrapper.success(page);
        log.info("/api/role/list pageNum={},role={},resp={}", pageNum, JSON.toJSONString(role), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【角色】新增或更新接口", description = "角色新增或更新接口")
    @PostMapping("/api/role/save")
    public RespWrapper<Boolean> roleSave(@RequestBody SysRole role) {
        log.info("/api/role/save role={}", JSON.toJSONString(role));
        RespWrapper<Boolean> resp;
        if (null != role && StrUtil.isNotBlank(role.getId())) {
            resp = sysRoleService.editRole(role);
        } else {
            resp = sysRoleService.addRole(role);
        }
        log.info("/api/role/save role={}, resp={}", JSON.toJSONString(role), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【角色】角色删除接口", description = "角色删除接口")
    @PostMapping("/api/role/delete/{roleId}")
    public RespWrapper<Boolean> deleteRole(@PathVariable("roleId") String roleId) {
        log.info("/api/role/delete/{}", roleId);
        RespWrapper<Boolean> resp = sysRoleService.deleteRole(roleId);
        log.info("/api/role/delete/{}, resp={}", roleId, JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【角色与菜单】查询接口", description = "根据角色查询对应的菜单查询接口")
    @PostMapping("/api/role_permission/{roleId}")
    public RespWrapper<List<SysPermission>> rolePermission(@PathVariable("roleId") String roleId) {
        log.info("/api/role_permission/{}", roleId);
        List<SysPermission> list = sysPermissionService.getSysPermissionByRoleId(roleId);
        RespWrapper<List<SysPermission>> resp = RespWrapper.success(list);
        log.info("/api/role_permission/{},resp={}", roleId, JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【角色与菜单】授权保存接口", description = "角色与菜单，授权保存接口")
    @PostMapping("/api/role_permission/save")
    public RespWrapper<Boolean> rolePermissionSave(@RequestBody SaveRolePermission save) {
        log.info(" /api/role_permission/save {}", JSON.toJSONString(save));
        RespWrapper<Boolean> resp = sysRoleService.saveRolePermission(save);
        log.info(" /api/role_permission/save {}, resp={}", JSON.toJSONString(save), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】状态枚举接口", description = "用户状态枚举查询接口")
    @PostMapping("/api/user/status-enum")
    public RespWrapper<Map<Integer, String>> userStatusEnum() {
        Map<Integer, String> statusMap = new LinkedHashMap<>();
        Arrays.stream(SysUserStatusEnum.values()).forEach(e -> statusMap.put(e.getCode(), e.getMsg()));
        RespWrapper<Map<Integer, String>> resp = RespWrapper.success(statusMap);
        log.info("/api/user/status-enum,{}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】列表分页查询接口", description = "用户列表分页查询接口")
    @PostMapping("/api/user/list")
    public RespWrapper<PageInfo<SysUser>> userList(
            @Parameter(name = "pageNum", description = "页码，当前第x页", example = "1")
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestBody(required = false) SysUser user) {
        log.info("/api/user/list pageNum={},{}", pageNum, JSON.toJSONString(user));
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        RespWrapper<PageInfo<SysUser>> resp = RespWrapper.success(page);
        log.info("/api/user/list pageNum={},{},{}", pageNum, JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】新增接口", description = "用户新增接口")
    @PostMapping("/api/user/add")
    public RespWrapper<Boolean> userAdd(@RequestBody SysUser user) {
        log.info("/api/user/add {}", JSON.toJSONString(user));
        SysUserDo userDo = SysUserConvert.INSTANCE.toDo(user);
        SysRoleDo roleDo = ObjectUtil.isEmpty(user) || ObjectUtil.isEmpty(user.getRoleId()) ? null : SysRoleDo.builder().id(user.getRoleId()).build();
        RespWrapper<Boolean> resp = sysUserService.addSysUser(userDo, roleDo);
        log.info("/api/user/add {},{}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】编辑接口", description = "用户编辑接口")
    @PostMapping("/api/user/edit")
    public RespWrapper<Boolean> userEdit(@RequestBody SysUser user) {
        log.info("/api/user/edit {}", JSON.toJSONString(user));
        RespWrapper<Boolean> resp = sysUserService.editUser(user);
        log.info("/api/user/edit {}, resp={}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】删除接口", description = "用户删除接口")
    @PostMapping("/api/user/delete/{userId}")
    public RespWrapper<Boolean> deleteUser(@PathVariable("userId") String userId) {
        log.info("/api/delete/user/{}", userId);
        RespWrapper<Boolean> resp = sysUserService.deleteUser(userId);
        log.info("/api/delete/user/{}, resp={}", userId, JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】重置密码接口", description = "用户重置密码接口")
    @PostMapping("/api/user/reset-pwd")
    public RespWrapper<Boolean> userResetPwd(@RequestBody SysUser user) {
        log.info("/api/user/reset-pwd {}", JSON.toJSONString(user));
        RespWrapper<Boolean> resp = sysUserService.resetPwd(user);
        log.info("/api/user/reset-pwd {}, response={}", JSON.toJSONString(user), JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "【用户】Excel导出接口", description = "用户Excel导出接口")
    @GetMapping("/api/user/export")
    @SneakyThrows
    public void userExport(HttpServletResponse response, SysUser user) {
        List<SysUser> list = sysUserService.findAll(user);

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("username", "账号");
        writer.addHeaderAlias("realName", "姓名");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("createUserRealName", "创建人");
        writer.addHeaderAlias("updateTime", "更新时间");
        writer.addHeaderAlias("updateUserRealName", "更新人");
        writer.addHeaderAlias("lastLoginTime", "最后登录时间");
        writer.addHeaderAlias("roleName", "角色");

        // 默认的，未添加的alias也会写出，如果想只显示加的别名这种字段，可以在这里设置关闭
        writer.setOnlyAlias(true);

        writer.write(list, true);

        String fileName = URLEncoder.encode("用户数据", StandardCharsets.UTF_8);
        // 浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        OutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream);
        // 关闭流
        writer.close();
        outputStream.close();
        log.info("用户信息导出成功");
    }


    @Operation(summary = "【用户】Excel导入接口", description = "用户Excel导入接口")
    @PostMapping("/api/user/import")
    @SneakyThrows
    public RespWrapper<Boolean> userImport(MultipartFile file) {
        log.info("api/user/import");
        InputStream stream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(stream);
        reader.addHeaderAlias("账号", "username");
        reader.addHeaderAlias("姓名", "realName");
        reader.addHeaderAlias("邮箱", "email");
        reader.addHeaderAlias("状态", "status");
        reader.addHeaderAlias("角色", "roleName");
        List<SysUser> list = reader.readAll(SysUser.class);
        // FIXME 这里只是展示如何接收前端上传的Excel数据，不做业务逻辑实现
        log.info("api/user/import, list={}", JSON.toJSONString(list));
        RespWrapper<Boolean> resp = RespWrapper.success(true);
        log.info("api/user/import, resp={}", JSON.toJSONString(resp));
        return resp;
    }

}
