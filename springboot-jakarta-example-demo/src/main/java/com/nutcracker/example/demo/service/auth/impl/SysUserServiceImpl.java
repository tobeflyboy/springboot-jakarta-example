package com.nutcracker.example.demo.service.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.exception.BusinessException;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserRoleMapper;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.util.salt.Digests;
import com.nutcracker.example.demo.util.salt.Encodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务impl
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:21:01
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysUserMapper sysUserMapper;

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(SysUserDo sysUserDo) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        sysUserDo.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(sysUserDo.getPassword().getBytes(), salt, HASH_INTERATIONS);
        sysUserDo.setPassword(Encodes.encodeHex(hashPassword));
    }

    @Transactional
    @Override
    public void addSysUser(SysUserDo sysUserDo, SysRoleDo sysRoleDo) {
        if (sysUserDo == null || sysRoleDo == null) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StrUtil.isBlank(sysUserDo.getUsername()) || StrUtil.isBlank(sysUserDo.getPassword())) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StrUtil.isBlank(sysRoleDo.getId())) {
            throw new BusinessException("user.registr.error", "用户未指定所属角色");
        }

        // Role r = daoService.getByPrimaryKey(Role.class, role.getId());
        SysRoleDo r = sysRoleMapper.selectById(sysRoleDo.getId());
        if (r == null) {
            throw new BusinessException("user.registr.error", "用户未指定所属组织或角色");
        }

        SysUserDo u = sysUserMapper.findUserByUsername(sysUserDo.getUsername());
        if (u != null) {
            throw new BusinessException("user.registr.error", "用户账号已经存在,username=" + sysUserDo.getUsername());
        }

        entryptPassword(sysUserDo);
        sysUserDo.setStatus(SysUserStatusEnum.VALID.getCode());
        sysUserDo.setCreateTime(LocalDateTime.now());
        sysUserDo.setId(String.valueOf(IdWorker.getId("T_USER")));
        sysUserMapper.insert(sysUserDo);

        SysUserRoleDo ur = new SysUserRoleDo();
        ur.setRoleId(r.getId());
        ur.setUserId(sysUserDo.getId());
        ur.setId(String.valueOf(IdWorker.getId("T_USER_ROLE")));
        // daoService.save(ur);
        sysUserRoleMapper.insert(ur);
    }

    @Override
    public void updatePassword(SysUserDo sysUserDo) {
        if (log.isDebugEnabled()) {
            log.debug("## update User password.");
        }
        SysUserDo u = sysUserMapper.selectById(sysUserDo.getId());
        u.setPassword(sysUserDo.getPassword());
        entryptPassword(u);
        u.setUpdateTime(LocalDateTime.now());
        sysUserMapper.updateById(u);
    }

    @Override
    public SysUserDo findByUsername(String username) {
        try {
            return sysUserMapper.findUserByUsername(username);
        } catch (Exception e) {
            log.error("# 根据账号查询用户报错 , username={}", username);
            throw new BusinessException("1001", "查询用户失败");
        }
    }

    @Transactional
    @Override
    public void updateLastLoginTime(SysUserDo sysUserDo) {
        if (null == sysUserDo || null == sysUserDo.getId()) {
            return;
        }
        SysUserDo u = sysUserMapper.selectById(sysUserDo.getId());
        if (u != null) {
            LocalDateTime lastLoginTime = u.getLastLoginTime();
            if (lastLoginTime == null) {
                lastLoginTime = LocalDateTime.now();
            }
            sysUserDo = new SysUserDo();
            sysUserDo.setId(u.getId());
            sysUserDo.setLastLoginTime(lastLoginTime);
            sysUserMapper.updateById(sysUserDo);
        }
    }

    @Override
    public PageInfo<SysUser> findSysUserByPage(Integer pageNum, SysUser user) {
        log.info("findSysUserByPage , pageNum={},{}", pageNum, user);
        pageNum = pageNum == null ? 1 : pageNum;
        PageHelper.startPage(pageNum, DemoConstants.PAGE_SIZE);
        List<SysUser> list = sysUserMapper.findUser(user);
        PageInfo<SysUser> page = new PageInfo<>(list);
        log.debug("findSysUserByPage page.toString()={}", page);
        return page;
    }
}
