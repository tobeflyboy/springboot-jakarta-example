package com.nutcracker.example.demo.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nutcracker.example.demo.entity.sys.SysMenuRole;
import com.nutcracker.example.demo.mapper.sys.SysMenuRoleMapper;
import com.nutcracker.example.demo.service.sys.SysMenuRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * sys menu role service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:38
 */
@Service
public class SysMenuRoleServiceImpl implements SysMenuRoleService {

    @Resource
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public int addMenuRole(SysMenuRole sysMenuRole) {
        return sysMenuRoleMapper.addMenuRole(sysMenuRole);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return sysMenuRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<String> getAllMenuId(String roleId, List<String> parentIds) {
        QueryWrapper<SysMenuRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("menu_id").eq("role_id", roleId).notIn("menu_id", parentIds);
        List<SysMenuRole> sysMenuRoles = sysMenuRoleMapper.selectList(queryWrapper);
        return sysMenuRoles.stream().map(o -> o.getMenuId()).collect(Collectors.toList());
    }
}
