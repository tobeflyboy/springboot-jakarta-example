package com.nutcracker.example.demo.config.security;

import com.nutcracker.common.domain.User;
import com.nutcracker.example.demo.convert.auth.SysRoleConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
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
import java.util.Calendar;

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
        SysRoleDo sysRoleDo = sysRoleService.findRoleByUserId(sysUserDo.getId());
        SysRole role = SysRoleConvert.INSTANCE.toDomain(sysRoleDo);
        User user = User.builder()
                .userId(sysUserDo.getId())
                .username(sysUserDo.getUsername())
                .realName(sysUserDo.getRealName())
                .roleId(role.getId())
                .roleCode(role.getRoleCode())
                .roleName(role.getRoleName())
                .build();
        WebUtil.setSessionUser(request, response, user);
        sysUserDo.setLastLoginTime(Calendar.getInstance().getTime());
        sysUserService.updateLastLoginTime(sysUserDo);

        log.info("onAuthenticationSuccess {} 登录成功", user.getUsername());

        // 调用父类的逻辑，处理重定向等操作
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

