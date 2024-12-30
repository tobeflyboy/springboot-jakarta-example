package com.nutcracker.example.demo.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * sys user service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:33
 */
public interface SysUserService {

    /**
     * 根据姓名查询
     *
     * @param name 姓名
     * @return 用户实例
     */
    SysUser findByName(@Param("name") String name);

    /**
     * 查询所有用户
     *
     * @param page 分页数据
     * @return 所有用户集合
     */
    IPage<SysUser> getAll(Page<SysUser> page);

    /**
     * 根据id查用户
     *
     * @param id id
     * @return 用户集合
     */
    SysUser getById(String id);

    /**
     * 根据id删除用户
     *
     * @param id id
     * @return 返回值
     */
    int deleteById(String id);

    /**
     * 根据id用户
     *
     * @param sysUser 用户
     * @return 返回值
     */
    int updateById(SysUser sysUser);

    /**
     * 保存用户
     *
     * @param sysUser 用户
     * @return 返回值
     */
    int insert(SysUser sysUser);

    /**
     * 根据用户id,更新密码
     *
     * @param password
     * @param id
     * @return
     */
    int updatePasswordById(String password, String id);
}
