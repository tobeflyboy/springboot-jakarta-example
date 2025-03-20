package com.nutcracker.example.demo.config.security;

import com.nutcracker.example.demo.convert.auth.SysRoleConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.service.auth.AuthService;
import com.nutcracker.example.demo.service.auth.SysPermissionService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.web.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 身份验证成功处理程序
 *
 * @author 胡桃夹子
 * @date 2025/03/20 10:20:30
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;
    private final SysPermissionService sysPermissionService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        SysUserDo sysUserDo = authService.findUserByName(token.getName());
        if (sysUserDo == null) {
            response.sendRedirect(request.getContextPath() + "/login");
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

        // 调用父类的逻辑，处理重定向等操作
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

