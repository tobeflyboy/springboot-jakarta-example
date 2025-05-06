package com.nutcracker.example.demo.service.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.nutcracker.common.domain.User;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.util.JwtUtil;
import com.nutcracker.common.util.SecurityUtils;
import com.nutcracker.common.wrapper.RespWrapper;
import com.nutcracker.example.demo.constant.CacheableKey;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.convert.auth.SysRoleConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.entity.domain.auth.UserRole;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserRoleMapper;
import com.nutcracker.example.demo.service.auth.AuthService;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.service.auth.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 权限接口
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:03:14
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public SysUserDo findUserByName(String username) {
        log.info("findUserByName , username={}", username);
        return sysUserMapper.findUserByUsername(username);
    }

    @Override
    public SysRoleDo findRoleByRoleCode(String roleCode) {
        log.info("findRoleByRoleCode , roleCode={}", roleCode);
        return sysRoleMapper.findRoleByRoleCode(roleCode);
    }

    @Override
    public List<SysUserDo> findUserByRoleCode(String roleCode) {
        log.info("findUserByRoleCode , roleCode={}", roleCode);
        return sysUserMapper.findUserByRoleCode(roleCode);
    }

    @Override
    public User parseToken(String token) {
        if(StrUtil.isBlank(token)){
            return null;
        }
        token = StrUtil.startWith(token, DemoConstants.TOKEN_PREFIX) ? token.substring(DemoConstants.TOKEN_PREFIX.length()) : token;
        return JwtUtil.parseToken(token, secret);
    }

    @Override
    @Cacheable(cacheNames = CacheableKey.SESSION_USER, key = "#token", condition = "#token != null", unless = "#result == null")
    public User getCurrentUser(String token) {
        User user = parseToken(token);
        if (user != null) {
            UserRole userRole = sysUserRoleMapper.findUserRole(user.getUserId(), user.getRoleId());
            if (userRole != null) {
                user.setUsername(userRole.getUsername());
                user.setRealName(userRole.getRealName());
                user.setRoleId(userRole.getRoleId());
                user.setRoleCode(userRole.getRoleCode());
                user.setRoleName(userRole.getRoleName());
            }
            return user;
        }
        return null;
    }

    @Override
    public RespWrapper<SessionUser> refreshToken(String oldToken) {
        User user = getCurrentUser(oldToken);
        if (user == null) {
            log.error("refreshToken error, token={}", oldToken);
            return RespWrapper.unAuthorized("token已失效！");
        }
        Date expiresAt = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        String token = JwtUtil.refreshToken(oldToken, expiresAt, secret);
        if (StrUtil.isBlank(token)) {
            return RespWrapper.unAuthorized("token已失效！");
        }
        SessionUser sessionUser = SessionUser.builder()
                .token(DemoConstants.TOKEN_PREFIX + token)
                .expiresAt(expiresAt)
                .user(user)
                .build();
        RespWrapper<SessionUser> resp = RespWrapper.success(sessionUser);
        log.info("refreshToken success, resp={}", JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public RespWrapper<SessionUser> login(String username, String password) {
        SysUserDo userDo = sysUserMapper.findUserByUsername(username);
        if (userDo == null) {
            log.error("login error, 账号不存在！username={}", username);
            return RespWrapper.fail("账号或密码错误！");
        }
        String pwd = SecurityUtils.encryptPassword(userDo.getSalt(), password, userDo.getUsername());
        if (!StrUtil.equals(pwd, userDo.getPassword())) {
            log.error("{}账号的密码不正确, 输入的密码：{}，数据库中的密码：{}", username, pwd, userDo.getPassword());
            return RespWrapper.fail("账号或密码错误！");
        }
        SysRoleDo sysRoleDo = sysRoleService.findRoleByUserId(userDo.getId());
        SysRole role = SysRoleConvert.INSTANCE.toDomain(sysRoleDo);
        User user = User.builder()
                .userId(userDo.getId())
                .username(userDo.getUsername())
                .realName(userDo.getRealName())
                .roleId(role.getId())
                .roleCode(role.getRoleCode())
                .roleName(role.getRoleName())
                .build();
        // 有效期为1天
        Date expiresAt = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        String token = JwtUtil.createToken(user, expiresAt, secret);
        SessionUser sessionUser = SessionUser.builder()
                .token(DemoConstants.TOKEN_PREFIX + token)
                .expiresAt(expiresAt)
                .user(user)
                .build();
        userDo.setLastLoginTime(Calendar.getInstance().getTime());
        sysUserService.updateLastLoginTime(userDo);
        RespWrapper<SessionUser> resp = RespWrapper.success(sessionUser);
        log.info("login success, resp={}", JSON.toJSONString(resp));
        return resp;
    }
}
