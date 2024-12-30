package com.nutcracker.example.demo.service.sys;


import com.nutcracker.example.demo.entity.sys.SysUserRole;

/**
 * sys user role service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:33
 */
public interface SysUserRoleService {

    /**
     * 添加用户和角色的联系
     *
     * @param sysUserRole 用户角色
     * @return 返回值
     */
    int insert(SysUserRole sysUserRole);

    /**
     * 根据用户id删除用户和角色的联系
     *
     * @param userId 用户id
     * @return 返回值
     */
    int deleteByUserId(String userId);
}
