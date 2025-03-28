package com.nutcracker.example.demo.service.auth;

import com.nutcracker.example.demo.entity.ApiResponse;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;

import java.util.List;

/**
 * 权限服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:44:58
 */
public interface SysPermissionService {

    /**
     * 查找所有系统权限
     *
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> findSysPermission();

    /**
     * 查询角色所能访问的所有菜单
     *
     * @param roleId 角色ID
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> getRolePermissionByRoleId(String roleId);

    /**
     * 保存菜单
     *
     * @param sysPermission 菜单
     */
    ApiResponse<Boolean> savePermission(SysPermission sysPermission);

    /**
     * 删除菜单权限
     *
     * @param permissionId 菜单权限id
     * @return {@link ApiResponse }<{@link Boolean }>
     */
    ApiResponse<Boolean> deletePermission(String permissionId);


    /**
     * 查询角色所拥有的所有菜单
     *
     * @param roleId 角色ID
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> getSysPermissionByRoleId(String roleId);

}
