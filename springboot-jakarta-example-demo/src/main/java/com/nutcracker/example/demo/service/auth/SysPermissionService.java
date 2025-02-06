package com.nutcracker.example.demo.service.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
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
     * 查询用户所能访问的所有菜单
     *
     * @param userId 用户ID
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> getMenuPermissionByUserId(String userId);

    /**
     * 添加 菜单
     *
     * @param sysPermissionDo 菜单项
     */
    void addPermission(SysPermissionDo sysPermissionDo);
}
