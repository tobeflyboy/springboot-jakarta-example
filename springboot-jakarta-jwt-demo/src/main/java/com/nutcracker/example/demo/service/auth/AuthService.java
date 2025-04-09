package com.nutcracker.example.demo.service.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;

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

}
