package com.nutcracker.example.demo.service.auth.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRolePermissionDo;
import com.nutcracker.example.demo.exception.BusinessException;
import com.nutcracker.example.demo.mapper.auth.SysPermissionMapper;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysRolePermissionMapper;
import com.nutcracker.example.demo.service.auth.RoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:17:22
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    @Transactional
    public void addRole(SysRoleDo sysRoleDo) {
        if (sysRoleDo == null || StrUtil.isBlank(sysRoleDo.getRoleCode()) || StrUtil.isBlank(sysRoleDo.getRoleName())) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("添加角色 : {}", sysRoleDo);
        }
        SysRoleDo r = findRoleByAuthority(sysRoleDo.getRoleCode());
        if (r == null) {
            sysRoleDo.setId(String.valueOf(IdWorker.getId("sys_role")));
            sysRoleMapper.insert(sysRoleDo);
        }
    }

    @Override
    public SysRoleDo findRoleByAuthority(String code) {
        if (log.isDebugEnabled()) {
            log.debug("根据编码查询角色 :　{}", code);
        }
        return sysRoleMapper.findRoleByRoleCode(code);
    }

    @Override
    public SysRoleDo findRoleByUserId(String userId) {
        List<SysRoleDo> roleDoList = sysRoleMapper.findRoleByUserId(userId);
        if (CollectionUtil.isNotEmpty(roleDoList)) {
            return roleDoList.get(0);
        }
        return null;
    }

    @Transactional
    @Override
    public void addRolePermission(String roleCode, String permissionCode) {
        SysRoleDo sysRoleDo = findRoleByAuthority(roleCode);
        if (sysRoleDo == null) {
            throw new BusinessException("role-fail", "给角色授权失败， 角色编码错误");
        }
        SysPermissionDo sysPermissionDo = sysPermissionMapper.findPermissionByPermissionCode(permissionCode);
        if (sysPermissionDo == null) {
            throw new BusinessException("role-fail", "给角色授权失败， 资源编码不存在，permissionCode=" + permissionCode);
        }

        SysRolePermissionDo rolePermissionDo = new SysRolePermissionDo();
        rolePermissionDo.setRoleId(sysRoleDo.getId());
        rolePermissionDo.setPermissionId(sysPermissionDo.getId());

        SysRolePermissionDo rp = sysRolePermissionMapper.findRolePermissionByRoleIdAndPermissionId(rolePermissionDo.getRoleId(), rolePermissionDo.getPermissionId());
        if (rp == null) {
            rolePermissionDo.setId(String.valueOf(IdWorker.getId("sys_role_permission")));
            sysRolePermissionMapper.insert(rolePermissionDo);
        }

    }
}
