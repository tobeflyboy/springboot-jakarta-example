package com.nutcracker.example.demo.web.rest;

import com.github.pagehelper.PageInfo;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.wrapper.RespWrapper;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
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

    @Operation(summary = "用户状态枚举", description = "用户状态枚举查询")
    @PostMapping("/api/enum/user-status")
    public RespWrapper<Map<Integer, String>> userStatus() {
        Map<Integer, String> statusMap = new LinkedHashMap<>();
        Arrays.stream(SysUserStatusEnum.values()).forEach(e -> statusMap.put(e.getCode(), e.getMsg()));
        RespWrapper<Map<Integer, String>> resp = RespWrapper.success(statusMap);
        log.info("/api/enum/user-status,{}", JSON.toJSONString(resp));
        return resp;
    }

    @Operation(summary = "用户列表接口", description = "用户列表分页查询")
    @PostMapping("/api/user/list")
    public RespWrapper<PageInfo<SysUser>> userList(
            @Parameter(name = "pageNum", description = "页码，当前第x页", example = "1")
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestBody(required = false) SysUser user) {
        log.info("/api/user/list pageNum={},{}", pageNum, user);
        PageInfo<SysUser> page = sysUserService.findSysUserByPage(pageNum, user);
        RespWrapper<PageInfo<SysUser>> resp = RespWrapper.success(page);
        log.info("/api/user/list pageNum={},{},{}", pageNum, user, resp);
        return resp;
    }


}
