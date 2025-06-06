package com.nutcracker.example.demo.service.auth;

import com.nutcracker.common.domain.User;
import com.nutcracker.common.wrapper.RespWrapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;

import java.util.List;

/**
 * 权限接口
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:43:13
 */
public interface AuthService {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return user 用户
     */
    SysUserDo findUserByName(String username);

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色对象
     */
    SysRoleDo findRoleByRoleCode(String roleCode);

    /**
     * 根据角色编码查询用户
     *
     * @param roleCode 角色编码
     * @return user 用户
     */
    List<SysUserDo> findUserByRoleCode(String roleCode);

    /**
     * 解析令牌
     *
     * @param token token
     * @return {@link User }
     */
    User parseToken(String token);

    /**
     * 获取当前用户
     *
     * @param token token
     * @return {@link User }
     */
    User getCurrentUser(String token);

    /**
     * 刷新令牌
     *
     * @param token token
     * @return {@link RespWrapper }<{@link SessionUser }>
     */
    RespWrapper<SessionUser> refreshToken(String token);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link RespWrapper }<{@link SessionUser }>
     */
    RespWrapper<SessionUser> login(String username, String password);

}
