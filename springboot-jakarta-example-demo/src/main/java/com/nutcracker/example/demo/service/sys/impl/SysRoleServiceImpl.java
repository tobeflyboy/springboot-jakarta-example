package com.nutcracker.example.demo.service.sys.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysRole;
import com.nutcracker.example.demo.mapper.sys.SysRoleMapper;
import com.nutcracker.example.demo.service.sys.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sys role service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:39
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleDao;

    @Override
    public SysRole findByUserId(String userId) {
        return sysRoleDao.findByUserId(userId);
    }

    @Override
    public IPage<SysRole> getAll(Page<SysRole> page) {
        return sysRoleDao.getAll(page);
    }

    @Override
    public SysRole getByName(String name) {
        return sysRoleDao.getByName(name);
    }

    @Override
    public String getById(String id) {
        return sysRoleDao.getById(id);
    }

    @Override
    public int deleteById(String id) {
        return sysRoleDao.deleteById(id);
    }

    @Override
    public int updateById(SysRole sysRole) {
        return sysRoleDao.updateById(sysRole);
    }

    @Override
    public int insert(SysRole sysRole) {
        return sysRoleDao.insert(sysRole);
    }

    @Override
    public List<String> getAllRoleName() {
        return sysRoleDao.getAllRoleName();
    }

    @Override
    public String getIdByName(String name) {
        return sysRoleDao.getIdByName(name);
    }
}
