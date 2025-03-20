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
     * 查找所有菜单
     *
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermissionDo> findAllSysPermission();

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param userId 用户
     * @return {@link List }<{@link SysPermissionDo }>
     */
    List<SysPermissionDo> findPermissionByUserId(@Param("userId") String userId);

    /**
     * 根据菜单KEY查询菜单
     *
     * @param permissionCode 菜单KEY
     * @return {@link SysPermissionDo }
     */
    SysPermissionDo findPermissionByPermissionCode(@Param("permissionCode") String permissionCode);

    /**
     * 查找菜单
     *
     * @param permission 许可
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> findSysPermission(SysPermission permission);

}
