package com.nutcracker.example.demo.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nutcracker.common.util.SecurityUtils;
import com.nutcracker.common.util.salt.Digests;
import com.nutcracker.common.util.salt.Encodes;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
import com.nutcracker.example.demo.mapper.CustomDateTypeHandler;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserRoleMapper;
import com.nutcracker.example.demo.service.auth.SysUserService;
import com.nutcracker.example.demo.web.Identify;
import com.nutcracker.common.exception.BusinessException;
import com.nutcracker.common.wrapper.RespWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
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
        String pwd = SecurityUtils.encryptPassword(sysUserDo.getSalt(), sysUserDo.getPassword(), sysUserDo.getUsername());
        sysUserDo.setPassword(pwd);
    }

    @Transactional
    @Override
    public RespWrapper<Boolean> addSysUser(SysUserDo userDo, SysRoleDo roleDo) {
        log.info("addSysUser {},{}", userDo, roleDo);
        if (userDo == null || roleDo == null) {
            log.error("addSysUser 缺少必要参数，新增用户失败！");
            return RespWrapper.validateFailed("缺少必要参数，新增用户失败！");
        }

        if (StrUtil.isBlank(userDo.getUsername()) || StrUtil.isBlank(userDo.getPassword())) {
            log.error("addSysUser {},{} 添加用户失败，新增用户失败！", userDo, roleDo);
            return RespWrapper.validateFailed("账号或密码错误，新增用户失败！");
        }

        if (StrUtil.isBlank(roleDo.getId())) {
            log.error("addSysUser {},{} 用户未指定所属角色，新增用户失败！", userDo, roleDo);
            return RespWrapper.validateFailed("用户未指定所属角色，新增用户失败！");
        }

        SysRoleDo r = sysRoleMapper.selectById(roleDo.getId());
        if (r == null) {
            log.error("addSysUser {},{} 用户未指定所属组织或角色，新增用户失败！", userDo, roleDo);
            return RespWrapper.validateFailed("用户未指定所属组织或角色，新增用户失败！");
        }

        SysUserDo u = sysUserMapper.findUserByUsername(userDo.getUsername());
        if (u != null) {
            log.error("addSysUser {},{} 用户账号已经存在，新增用户失败！", userDo, roleDo);
            return RespWrapper.fail("用户账号已经存在，新增用户失败！");
        }
        String createdBy = Identify.getSessionUser().getUserId();
        Date now = Calendar.getInstance().getTime();
        entryptPassword(userDo);
        userDo.setId(String.valueOf(IdWorker.getId("sys_user")));
        userDo.setStatus(SysUserStatusEnum.VALID.getCode());
        userDo.setCreateTime(now);
        userDo.setCreateBy(createdBy);
        int ret = sysUserMapper.insert(userDo);
        if (ret == 0) {
            log.error("addSysUser {},{} 新增用户失败！", userDo, roleDo);
            return RespWrapper.fail("新增用户失败！");
        }

        SysUserRoleDo ur = new SysUserRoleDo();
        ur.setId(String.valueOf(IdWorker.getId("sys_user_role")));
        ur.setRoleId(r.getId());
        ur.setUserId(userDo.getId());
        ur.setCreateTime(now);
        ur.setCreateBy(createdBy);
        ret = sysUserRoleMapper.insert(ur);
        if (ret == 0) {
            log.error("addSysUser {},{} 新增用户失败！", userDo, roleDo);
            return RespWrapper.fail("新增用户失败！");
        }
        return RespWrapper.success(true);
    }

    @Override
    public void updatePassword(SysUserDo sysUserDo) {
        if (log.isDebugEnabled()) {
            log.debug("## update User password.");
        }
        SysUserDo u = sysUserMapper.selectById(sysUserDo.getId());
        u.setPassword(sysUserDo.getPassword());
        entryptPassword(u);
        u.setUpdateTime(Calendar.getInstance().getTime());
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
            Date lastLoginTime = u.getLastLoginTime();
            if (lastLoginTime == null) {
                lastLoginTime = Calendar.getInstance().getTime();
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

    @Transactional
    @Override
    public RespWrapper<Boolean> deleteUser(String userId) {
        log.info("deleteUser , userId={}", userId);
        if (StrUtil.isBlank(userId)) {
            return RespWrapper.validateFailed("删除失败，用户id为空！");
        }
        SysUserDo userDo = sysUserMapper.selectById(userId);
        if (null == userDo) {
            return RespWrapper.validateFailed("删除失败，用户不存在！");
        }

        List<SysUserRoleDo> list = sysUserRoleMapper.findUserRoleByUserId(userId);
        if (CollUtil.isNotEmpty(list)) {
            int ret = sysUserRoleMapper.delete(new LambdaUpdateWrapper<SysUserRoleDo>().eq(SysUserRoleDo::getUserId, userId));
            if (ret == 0) {
                log.error("deleteUser, sysUserRoleMapper.delete fail, userId={}", userId);
                return RespWrapper.fail("删除失败！");
            }
        }
        if (0 == sysUserMapper.deleteById(userId)) {
            return RespWrapper.fail("删除失败！");
        }
        return RespWrapper.success(true);
    }

    @Transactional
    @Override
    public RespWrapper<Boolean> editUser(SysUser user) {
        log.info("editUser, {}", user);
        if (ObjectUtil.isEmpty(user) || StrUtil.isBlank(user.getUserId())) {
            return RespWrapper.validateFailed("编辑保存失败，缺失用户信息！");
        }
        if (StrUtil.isAllBlank(user.getRoleId(), user.getEmail())) {
            return RespWrapper.validateFailed("编辑保存失败，用户角色必选，用户邮箱必填写！");
        }
        if (ObjectUtil.isEmpty(user.getStatus())) {
            return RespWrapper.validateFailed("编辑保存失败，用户状态未指定！");
        }
        SysUserDo userDo = sysUserMapper.selectById(user.getUserId());
        if (null == userDo) {
            return RespWrapper.validateFailed("编辑保存失败，用户不存在！");
        }

        Date now = Calendar.getInstance().getTime();
        String operator = Identify.getSessionUser().getUserId();
        // 更新用户状态、邮箱
        int updateResult = sysUserMapper.update(
                new LambdaUpdateWrapper<SysUserDo>()
                        .eq(SysUserDo::getId, user.getUserId())
                        .set(SysUserDo::getStatus, user.getStatus())
                        .set(SysUserDo::getEmail, user.getEmail())
                        .set(SysUserDo::getUpdateTime, now, "typeHandler=" + CustomDateTypeHandler.class.getName())
                        .set(SysUserDo::getUpdateBy, operator)
        );
        if (updateResult == 0) {
            log.error("editUser, sysUserMapper.update fail, {},now={},operator={}", user, now, operator);
            return RespWrapper.fail("编辑保存失败！");
        }
        List<SysUserRoleDo> list = sysUserRoleMapper.findUserRoleByUserId(user.getUserId());
        if (CollUtil.isEmpty(list)) {
            // 用户找不到角色，给用户新增角色
            SysUserRoleDo userRoleDo = SysUserRoleDo.builder()
                    .roleId(user.getRoleId())
                    .userId(user.getUserId())
                    .createTime(now)
                    .createBy(operator)
                    .build();
            if (sysUserRoleMapper.insert(userRoleDo) == 0) {
                log.error("editUser, sysUserRoleMapper.insert fail, {},now={},operator={}", userRoleDo, now, operator);
                return RespWrapper.fail("编辑保存失败！");
            }
        } else {
            if (CollUtil.size(list) > 1) {
                // 清理冗余角色记录（保留第一条）
                list.subList(1, list.size()).forEach(role ->
                        sysUserRoleMapper.deleteById(role.getId())
                );
            }
            SysUserRoleDo userRoleDo = list.get(0);
            if (!StrUtil.equals(user.getRoleId(), userRoleDo.getRoleId())) {
                userRoleDo.setRoleId(user.getRoleId());
                if (sysUserRoleMapper.updateById(userRoleDo) == 0) {
                    log.error("editUser, sysUserRoleMapper.updateById fail, {},now={},operator={}", userRoleDo, now, operator);
                    return RespWrapper.fail("编辑保存失败！");
                }
            }
        }
        return RespWrapper.success(true);
    }

    @Transactional
    @Override
    public RespWrapper<Boolean> resetPwd(SysUser user) {
        log.info("resetPwd, {}", user);
        if (ObjectUtil.isEmpty(user) || StrUtil.isBlank(user.getUserId())) {
            return RespWrapper.validateFailed("重置密码失败，缺失用户信息！");
        }
        if (StrUtil.isBlank(user.getNewPassword())) {
            return RespWrapper.validateFailed("请输入密码！");
        }
        SysUserDo userDo = sysUserMapper.selectById(user.getUserId());
        if (null == userDo) {
            return RespWrapper.validateFailed("重置密码失败，用户不存在！");
        }
        String password = SecurityUtils.encryptPassword(userDo.getSalt(), user.getNewPassword(), userDo.getUsername());
        Date now = Calendar.getInstance().getTime();
        String operator = Identify.getSessionUser().getUserId();
        // 更新用户状态、邮箱
        int updateResult = sysUserMapper.update(
                new LambdaUpdateWrapper<SysUserDo>()
                        .eq(SysUserDo::getId, user.getUserId())
                        .set(SysUserDo::getPassword, password)
                        .set(SysUserDo::getUpdateTime, now, "typeHandler=" + CustomDateTypeHandler.class.getName())
                        .set(SysUserDo::getUpdateBy, operator)
        );
        if (updateResult == 0) {
            log.error("resetPwd, sysUserMapper.update fail, {},now={},operator={}", user, now, operator);
            return RespWrapper.fail("重置密码失败！");
        }
        return RespWrapper.success(true);
    }

    @Override
    public List<SysUser> findAll(SysUser user) {
        return sysUserMapper.findUser(user);
    }
}
