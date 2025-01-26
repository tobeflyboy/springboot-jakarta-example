package com.nutcracker.example.demo.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:39:40
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDo> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link SysUserDo }
     */
    SysUserDo findUserByUsername(@Param("username") String username);

    /**
     * 根据用户名查询用户
     *
     * @param roleCode 用户名
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> findUserByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 根据条件（店铺、名称）查询客服人员
     *
     * @param roleCode 角色代码
     * @param status   状态
     * @param realName 用户姓名
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> findUser(@Param("roleCode") String roleCode, @Param("status") Integer status, @Param("realName") String realName);

    /**
     * 查询组织下所有客服员工
     *
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> findUsers();
}
