package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色映射器
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:40:45
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleDo> {

    /**
     * 通过用户名和角色代码查找用户角色
     *
     * @param username 用户名
     * @param roleCode 角色代码
     * @return {@link SysUserRoleDo }
     */
    SysUserRoleDo findUserRoleByUsernameAndRoleCode(@Param("username") String username, @Param("roleCode") String roleCode);

    /**
     * 按用户id查找用户角色
     *
     * @param userId 使用id
     * @return {@link List }<{@link SysUserRoleDo }>
     */
    List<SysUserRoleDo> findUserRoleByUserId(@Param("userId") String userId);

    /**
     * 按角色id查找用户角色
     *
     * @param roleId 角色ID
     * @return {@link List }<{@link SysUserRoleDo }>
     */
    List<SysUserRoleDo> findUserRoleByRoleId(@Param("roleId") String roleId);

}
