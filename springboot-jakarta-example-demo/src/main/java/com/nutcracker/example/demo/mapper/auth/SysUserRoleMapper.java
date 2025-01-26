package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
