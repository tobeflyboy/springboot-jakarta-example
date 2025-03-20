package com.nutcracker.example.demo.service.auth.impl;

import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserMapper;
import com.nutcracker.example.demo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限接口
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:03:14
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public SysUserDo findUserByName(String username) {
        log.info("findUserByName , username={}", username);
        return sysUserMapper.findUserByUsername(username);
    }

    @Override
    public SysRoleDo findRoleByRoleCode(String roleCode) {
        log.info("findRoleByRoleCode , roleCode={}", roleCode);
        return sysRoleMapper.findRoleByRoleCode(roleCode);
    }

    @Override
    public List<SysUserDo> findUserByRoleCode(String roleCode) {
        log.info("findUserByRoleCode , roleCode={}", roleCode);
        return sysUserMapper.findUserByRoleCode(roleCode);
    }

}
