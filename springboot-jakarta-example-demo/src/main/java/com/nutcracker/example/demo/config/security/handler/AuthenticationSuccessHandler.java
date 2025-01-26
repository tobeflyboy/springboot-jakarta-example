package com.nutcracker.example.demo.config.security.handler;

import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.ApiResponse;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.vo.auth.SysPermissionVo;
import com.nutcracker.example.demo.service.auth.AuthService;
import com.nutcracker.example.demo.service.auth.PermissionService;
import com.nutcracker.example.demo.service.auth.RoleService;
import com.nutcracker.example.demo.util.JSON;
import com.nutcracker.example.demo.util.ResponseUtils;
import com.nutcracker.example.demo.vo.LoginUserVo;
import com.nutcracker.example.demo.web.util.WebUtil;
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
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

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
            List<SysPermissionVo> permissions = permissionService.getMenuPermissionByUserId(sysUserDo.getId());
            SysRoleDo sysRoleDo = roleService.findRoleByUserId(sysUserDo.getId());
            LoginUserVo loginUserVo = LoginUserVo.builder()
                    .id(sysUserDo.getId())
                    .username(sysUserDo.getUsername())
                    .realName(sysUserDo.getRealName())
                    .permissions(permissions)
                    .sysRoleDo(sysRoleDo)
                    .build();
            WebUtil.setLoginUser(request, loginUserVo);
            String name = token.getName();
            log.info("onAuthenticationSuccess 用户：{} 登录成功", name);
            //保存日志
            ResponseUtils.print(response, LOGIN_SUCCESS);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }


}
