package com.nutcracker.example.demo.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys role mapper
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:24
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    //@Select("SELECT * FROM sys_role WHERE id =(SELECT role_id FROM sys_user_role WHERE user_id = #{userId})")
    SysRole findByUserId(@Param("userId") String userId);


    //@Select("SELECT r.* FROM sys_role AS r LEFT JOIN sys_user_role as u ON r.id = u.role_id " +
    //        "WHERE u.user_id = (SELECT id FROM sys_user WHERE name = #{name})")
    SysRole findByName(@Param("name") String name);

    /**
     * 查询所有角色
     *
     * @param page 分页数据
     * @return 所有角色集合
     */
    //@Select("SELECT * FROM sys_role")
    IPage<SysRole> getAll(Page<SysRole> page);

    /**
     * 根据名称查角色
     *
     * @param name 名称
     * @return 角色
     */
    //@Select("select * from sys_role where name = #{name}")
    SysRole getByName(@Param("name") String name);

    /**
     * 根据id查角色名称
     *
     * @param id id
     * @return 角色名称
     */
    //@Select("select distinct r.name from sys_role r left join sys_user_role ur on r.id = ur.role_id where ur.role_id = (\n" +
    //        "select urs.role_id from sys_user u left join sys_user_role urs on u.id = urs.user_id where u.id = #{id})")
    String getById(@Param("id") String id);

    /**
     * 获取所有的角色名称
     *
     * @return 所有角色名称
     */
    //@Select("select name from sys_role order by create_time")
    List<String> getAllRoleName();

    /**
     * 根据角色名称查询角色id
     *
     * @param name 角色名称
     * @return 角色id
     */
    //@Select("select id from sys_role where name = #{name} order by create_time")
    String getIdByName(@Param("name") String name);
}
