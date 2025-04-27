package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:33:39
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermissionDo> {

    /**
     * 按id更新菜单
     *
     * @param sysPermission 菜单
     * @return int
     */
    int updateSysPermissionById(SysPermissionDo sysPermission);

    /**
     * 按id更新菜单父编码
     *
     * @param newParentPermissionCode 新父权限代码
     * @param oldParentPermissionCode 旧父权限代码
     * @return int
     */
    int updateParentPermissionCode(String newParentPermissionCode, String oldParentPermissionCode);

    /**
     * 查找所有菜单
     *
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermissionDo> findAll();

    /**
     * 查询角色所能访问的所有菜单
     *
     * @param roleId 角色id
     * @return {@link List }<{@link SysPermissionDo }>
     */
    List<SysPermissionDo> getSysPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 根据菜单KEY查询菜单
     *
     * @param permissionCode 菜单KEY
     * @return {@link SysPermissionDo }
     */
    SysPermissionDo findByPermissionCode(@Param("permissionCode") String permissionCode);

    /**
     * 查询子菜单
     *
     * @param parentPermissionCode 父菜单编码
     * @return {@link List }<{@link SysPermissionDo }>
     */
    List<SysPermissionDo> findByParentPermissionCode(@Param("parentPermissionCode") String parentPermissionCode);


    /**
     * 查找全部菜单，同时判断角色有没有权限
     *
     * @param roleId 角色ID
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> findAllByRoleId(@Param("roleId") String roleId);

}
