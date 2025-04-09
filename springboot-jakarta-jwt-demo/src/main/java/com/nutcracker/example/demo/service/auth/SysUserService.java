package com.nutcracker.example.demo.service.auth;

import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.wrapper.RespWrapper;

import java.util.List;

/**
 * 用户服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:20:56
 */
public interface SysUserService {

    /**
     * 新增用户
     *
     * @param sysUserDo 用户
     * @param sysRoleDo 角色
     */
    RespWrapper<Boolean> addSysUser(SysUserDo sysUserDo, SysRoleDo sysRoleDo);


    /**
     * 更新密码
     *
     * @param u 用户对象
     */
    void updatePassword(SysUserDo u);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return user 用户
     */
    SysUserDo findByUsername(String username);


    /**
     * 更新用户上次登录时间
     *
     * @param sysUserDo 用户对照
     */
    void updateLastLoginTime(SysUserDo sysUserDo);

    /**
     * 分页查询用户
     *
     * @param pageNum 当前页码
     * @param user  {@link SysUser }
     * @return {@link List }<{@link SysUser }>
     */
    PageInfo<SysUser> findSysUserByPage(Integer pageNum , SysUser user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return {@link RespWrapper }<{@link Boolean }>
     */
    RespWrapper<Boolean> deleteUser(String userId);

    /**
     * 编辑用户
     *
     * @param user 用户
     * @return {@link RespWrapper }<{@link Boolean }>
     */
    RespWrapper<Boolean> editUser(SysUser user);

    /**
     * 重置用户密码
     *
     * @param user 用户
     * @return {@link RespWrapper }<{@link Boolean }>
     */
    RespWrapper<Boolean> resetPwd(SysUser user);
}
