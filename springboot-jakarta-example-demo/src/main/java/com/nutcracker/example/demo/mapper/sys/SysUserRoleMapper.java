package com.nutcracker.example.demo.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.sys.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys user role mapper
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:25
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id删除用户和角色的联系
     *
     * @param userId 用户id
     * @return 返回值
     */
    //@Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteByUserId(String userId);
}
