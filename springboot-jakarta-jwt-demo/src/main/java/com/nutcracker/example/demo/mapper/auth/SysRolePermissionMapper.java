package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRolePermissionDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与资源关系Mapper
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:37:39
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermissionDo> {

    /**
     * 查找角色资源
     *
     * @param roleId       角色id
     * @param permissionId 资源id
     * @return {@link SysRolePermissionDo }
     */
    SysRolePermissionDo findRolePermissionByRoleIdAndPermissionId(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

    /**
     * 按角色id删除
     *
     * @param roleId 角色ID
     * @return int
     */
    int deleteRolePermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 批量插入
     *
     * @param list 列表
     * @return int
     */
    int batchInsert(List<SysRolePermissionDo> list);

}
