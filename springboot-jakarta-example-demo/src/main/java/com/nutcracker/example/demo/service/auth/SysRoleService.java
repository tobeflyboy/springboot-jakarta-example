package com.nutcracker.example.demo.service.auth;

import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;

import java.util.List;

/**
 * 角色服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:16:46
 */
public interface SysRoleService {

    /**
     * 添加一个角色 ，若已经存在同名角色，则不创建
     *
     * @param sysRoleDo 角色对象
     */
    void addSysRole(SysRoleDo sysRoleDo);

    /**
     * 根据编码查询角色
     *
     * @param code 角色编码
     * @return {@link SysRoleDo }
     */
    SysRoleDo findByRoleCode(String code);

    /**
     * 根据用户查询对应所有角色
     *
     * @param userId 用户Id
     * @return {@link List }<{@link SysRoleDo }>
     */
    SysRoleDo findRoleByUserId(String userId);

    /**
     * 给角色授权
     *
     * @param roleCode      角色编码
     * @param permissionCode 授权对应的KEY
     */
    void addRolePermission(String roleCode, String permissionCode);

    /**
     * 分页查询角色
     *
     * @param pageNum 当前页码
     * @param user  {@link SysRole }
     * @return {@link List }<{@link SysRole }>
     */
    PageInfo<SysRole> findSysRoleByPage(Integer pageNum , SysRole user);

}
