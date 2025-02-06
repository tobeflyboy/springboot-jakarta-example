package com.nutcracker.example.demo.config.security;

import cn.hutool.core.util.StrUtil;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.service.auth.AuthService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.util.SecurityUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * CustomAuthenticationProvider
 *
 * @author 胡桃夹子
 * @date 2022/12/23 08:27
 */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private AuthService authService;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = (String) token.getCredentials();
        log.debug("authenticate username={},password={}", username, password);
        SysUserDo sysUserDo = authService.findUserByName(username);
        if (sysUserDo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 使用自定义 PasswordEncoder 进行密码匹配
        String pwd = SecurityUtils.encryptPassword(sysUserDo.getSalt(), password, sysUserDo.getUsername());
        if (!StrUtil.equals(pwd, sysUserDo.getPassword())) {
            log.error("密码不正确, 输入的密码：{}，数据库中的密码：{}", password, sysUserDo.getPassword());
            throw new UsernameNotFoundException("密码不正确");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 查询权限
        SysRoleDo sysRoleDo = sysRoleService.findRoleByUserId(sysUserDo.getId());
        authorities.add(new SimpleGrantedAuthority(sysRoleDo.getRoleCode()));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
