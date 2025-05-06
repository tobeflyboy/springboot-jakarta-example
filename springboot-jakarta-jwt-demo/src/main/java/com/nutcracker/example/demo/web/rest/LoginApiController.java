package com.nutcracker.example.demo.web.rest;

import com.github.xingfudeshi.knife4j.annotations.ApiOperationSupport;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.wrapper.RespCode;
import com.nutcracker.common.wrapper.RespWrapper;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录api控制器
 *
 * @author 胡桃夹子
 * @date 2025/04/27 15:01:22
 */
@Tag(name = "登录", description = "登录有关的接口")
@RequiredArgsConstructor
@Slf4j
@RestController
public class LoginApiController {

    private final AuthService authService;

    @ApiOperationSupport(order = 2)
    @Operation(summary = "登录接口", description = "基于账号密码登录接口")
    @PostMapping("/login")
    public RespWrapper<SessionUser> login(@RequestBody SysUser sysUser) {
        RespWrapper<SessionUser> resp = authService.login(sysUser.getUsername(), sysUser.getPassword());
        log.info("/login,{}", JSON.toJSONString(resp));
        return resp;
    }

    @ApiOperationSupport(order = 1)
    @Operation(summary = "刷新Token", description = "刷新系统凭证")
    @GetMapping(value = "/refresh-token")
    public RespWrapper<SessionUser> refreshToken(@RequestHeader("token") String token) {
        if (StringUtils.isBlank(token) || !token.startsWith(DemoConstants.TOKEN_PREFIX)) {
            log.error("token is empty.");
            return RespWrapper.fail(RespCode.UNAUTHORIZED);
        }
        RespWrapper<SessionUser> resp = authService.refreshToken(token);
        log.info("/refresh-token,{}", JSON.toJSONString(resp));
        return resp;
    }


}
