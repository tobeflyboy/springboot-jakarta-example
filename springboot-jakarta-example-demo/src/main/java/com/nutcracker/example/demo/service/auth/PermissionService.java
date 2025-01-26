package com.nutcracker.example.demo.service.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.vo.auth.SysPermissionVo;

import java.util.List;

/**
 * 权限服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:44:58
 */
public interface PermissionService {

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param userId 用户ID
     * @return {@link List }<{@link SysPermissionVo }>
     */
    List<SysPermissionVo> getMenuPermissionByUserId(String userId);

    /**
     * 添加 菜单
     *
     * @param sysPermissionDo 菜单项
     */
    void addPermission(SysPermissionDo sysPermissionDo);
}
