package com.nutcracker.example.demo.config.security.handler;

import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.convert.auth.SysRoleConvert;
import com.nutcracker.example.demo.entity.ApiResponse;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.service.auth.AuthService;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.util.JSON;
import com.nutcracker.example.demo.util.ResponseUtils;
import com.nutcracker.example.demo.web.WebUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * AuthenticationSuccessHandler
 *
 * @author 胡桃夹子
 * @date 2022/12/23 08:27
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String LOGIN_SUCCESS = JSON.toJSONString(ApiResponse.success(DemoConstants.LOGIN_SUCCESS));

    @Resource
    private AuthService authService;

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("onAuthenticationSuccess {}", request.getRequestURI());
        if (WebUtil.isAjax(request)) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            SysUserDo sysUserDo = authService.findUserByName(token.getName());
            if (sysUserDo == null) {
                response.sendRedirect(request.getContextPath() + "/403");
                return;
            }
            List<SysPermission> permissions = sysPermissionService.getMenuPermissionByUserId(sysUserDo.getId());
            SysRoleDo sysRoleDo = sysRoleService.findRoleByUserId(sysUserDo.getId());
            SysRole role = SysRoleConvert.INSTANCE.toDomain(sysRoleDo);
            SessionUser sessionUser = SessionUser.builder()
                    .id(sysUserDo.getId())
                    .username(sysUserDo.getUsername())
                    .realName(sysUserDo.getRealName())
                    .permissions(permissions)
                    .sysRole(role)
                    .build();
            WebUtil.setSessionUser(request, response, sessionUser);
            sysUserDo.setLastLoginTime(LocalDateTime.now());
            sysUserService.updateLastLoginTime(sysUserDo);

            log.info("onAuthenticationSuccess {} 登录成功", sessionUser.getUsername());
            //保存日志
            ResponseUtils.print(response, LOGIN_SUCCESS);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }


}
