package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:37:11
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleDo> {

    /**
     * 根据用户查询对应所有角色
     *
     * @param userId 用户
     * @return {@link List }<{@link SysRoleDo }>
     */
    List<SysRoleDo> findRoleByUserId(@Param("userId") String userId);

    /**
     * 根据编码查询角色
     *
     * @param roleCode 角色编码
     * @return {@link SysRoleDo }
     */
    SysRoleDo findRoleByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 查找系统角色
     *
     * @param sysRole 系统角色
     * @return {@link List }<{@link SysRole }>
     */
    List<SysRole> findSysRole(SysRole sysRole);

}
