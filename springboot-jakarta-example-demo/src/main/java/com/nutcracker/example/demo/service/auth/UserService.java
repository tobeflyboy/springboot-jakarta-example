package com.nutcracker.example.demo.service.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;

import java.util.List;

/**
 * 用户服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:20:56
 */
public interface UserService {

    /**
     * 新增用户
     *
     * @param sysUserDo 用户
     * @param sysRoleDo 角色
     */
    void addUser(SysUserDo sysUserDo, SysRoleDo sysRoleDo);


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
    SysUserDo findUserByName(String username);


    /**
     * 更新用户上次登录时间
     *
     * @param sysUserDo 用户对照
     */
    void updateUserLastLoginTime(SysUserDo sysUserDo);

    /**
     * 查询组织下所有客服员工
     *
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> findUsers();

    /**
     * 根据条件（店铺、名称）查询客服人员
     *
     * @param empName 客服人员名称
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> findEmp(String empName);

}
