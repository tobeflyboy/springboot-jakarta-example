package com.nutcracker.example.demo.config.security;

import com.nutcracker.example.demo.entity.sys.SysRole;
import com.nutcracker.example.demo.entity.sys.SysUser;
import com.nutcracker.example.demo.service.sys.SysRoleService;
import com.nutcracker.example.demo.service.sys.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * UserDetailServiceImpl
 *
 * @author 胡桃夹子
 * @date 2022/12/23 10:47
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 登录验证方法，前端发起 /login post请求，请求数据类型 content-type:【application/x-www-form-urlencoded; charset=UTF-8】
     *
     * @param username 用户名
     * @return {@link UserDetails }
     * @throws UsernameNotFoundException 找不到用户名异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername username={}", username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 查询用户
        SysUser sysUser = sysUserService.findByName(username);
        if (sysUser != null) {
            // 查询权限
            SysRole sysRole = sysRoleService.findByUserId(sysUser.getId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getAuthority()));
            return new User(username, sysUser.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("用户名不存在");
        }

    }
}
