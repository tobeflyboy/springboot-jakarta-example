package com.nutcracker.example.demo.service.sys.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysUser;
import com.nutcracker.example.demo.mapper.sys.SysUserMapper;
import com.nutcracker.example.demo.service.sys.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * sys user service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:42
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserDao;

    @Override
    public SysUser findByName(String name) {
        return sysUserDao.findByName(name);
    }

    @Override
    public IPage<SysUser> getAll(Page<SysUser> page) {
        return sysUserDao.getAll(page);
    }

    @Override
    public SysUser getById(String id) {
        return sysUserDao.getById(id);
    }

    @Override
    public int deleteById(String id) {
        return sysUserDao.deleteById(id);
    }

    @Override
    public int updateById(SysUser sysUser) {
        return sysUserDao.updateById(sysUser);
    }

    @Override
    public int insert(SysUser sysUser) {
        return sysUserDao.insert(sysUser);
    }

    @Override
    public int updatePasswordById(String password, String id) {
        return sysUserDao.updatePasswordById(password, id);
    }
}
