package com.nutcracker.example.demo.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.constant.CacheableKey;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.convert.auth.SysRoleConvert;
import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRolePermissionDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SaveRolePermission;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;
import com.nutcracker.example.demo.mapper.auth.SysPermissionMapper;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysRolePermissionMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserRoleMapper;
import com.nutcracker.example.demo.service.auth.SysRoleService;
import com.nutcracker.example.demo.web.Identify;
import com.nutcracker.common.exception.BusinessException;
import com.nutcracker.common.wrapper.RespWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 角色服务
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:17:22
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional
    public RespWrapper<Boolean> addRole(SysRole sysRole) {
        if (sysRole == null || StrUtil.isBlank(sysRole.getRoleCode()) || StrUtil.isBlank(sysRole.getRoleName())) {
            return RespWrapper.validateFailed("新增角色失败，角色编码、角色名称不能为空！");
        }
        if (log.isDebugEnabled()) {
            log.debug("添加角色 : {}", sysRole);
        }
        SysRoleDo sysRoleDo = findByRoleCode(sysRole.getRoleCode());
        if (sysRoleDo != null) {
            return RespWrapper.validateFailed("新增角色失败，角色已不存在！");
        }
        sysRoleDo = SysRoleConvert.INSTANCE.toDo(sysRole);
        sysRoleDo.setId(String.valueOf(IdWorker.getId("sys_role")));
        sysRoleDo.setCreateBy(Identify.getSessionUser().getUserId());
        sysRoleDo.setCreateTime(Calendar.getInstance().getTime());
        int ret = sysRoleMapper.insert(sysRoleDo);
        if (ret == 1) {
            return RespWrapper.success(true);
        }
        return RespWrapper.fail("新增角色失败！");
    }

    @Override
    public SysRoleDo findByRoleCode(String code) {
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
        SysRoleDo sysRoleDo = findByRoleCode(roleCode);
        if (sysRoleDo == null) {
            throw new BusinessException("role-fail", "给角色授权失败， 角色编码错误");
        }
        SysPermissionDo sysPermissionDo = sysPermissionMapper.findByPermissionCode(permissionCode);
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

    @Override
    public PageInfo<SysRole> findSysRoleByPage(Integer pageNum, SysRole role) {
        log.info("findSysRoleByPage , pageNum={},{}", pageNum, role);
        pageNum = pageNum == null ? 1 : pageNum;
        PageHelper.startPage(pageNum, DemoConstants.PAGE_SIZE);
        List<SysRole> list = sysRoleMapper.findSysRole(role);
        PageInfo<SysRole> page = new PageInfo<>(list);
        log.debug("findSysRoleByPage page.toString()={}", page);
        return page;
    }

    @Override
    public RespWrapper<Boolean> editRole(SysRole role) {
        log.info("editRole , role={}", role);
        if (role == null || StrUtil.isAllBlank(role.getId(), role.getRoleCode(), role.getRoleName())) {
            return RespWrapper.validateFailed("更新角色失败，角色编码、角色名称不能为空！");
        }
        SysRoleDo sysRoleDo = sysRoleMapper.selectById(role.getId());
        if (null == sysRoleDo) {
            return RespWrapper.validateFailed("更新角色失败，角色已不存在！");
        }
        sysRoleDo = sysRoleMapper.findRoleByRoleCode(role.getRoleCode());
        if (null != sysRoleDo && !StrUtil.equals(role.getId(), sysRoleDo.getId())) {
            log.warn("editRole 更新角色失败，角色编码已经存在了！{}", sysRoleDo);
            return RespWrapper.fail("更新角色失败，角色编码已经存在了！");
        }
        sysRoleDo = SysRoleConvert.INSTANCE.toDo(role);
        int ret = sysRoleMapper.updateById(sysRoleDo);
        if (ret == 1) {
            return RespWrapper.success(true);
        }
        return RespWrapper.fail("更新角色失败！");
    }

    @Override
    @CacheEvict(cacheNames = CacheableKey.ROLE_PERMISSION, key = "#saveRolePermission.roleId", condition = "#saveRolePermission!=null && #saveRolePermission.roleId!=null")
    @Transactional
    public RespWrapper<Boolean> saveRolePermission(SaveRolePermission saveRolePermission) {
        log.info("saveRolePermission , saveRolePermission={}", saveRolePermission);
        if (saveRolePermission == null || StrUtil.isBlank(saveRolePermission.getRoleId())) {
            return RespWrapper.validateFailed("角色授权失败，角色信息不存在！");
        }
        if (saveRolePermission.getPermissionIdList() == null || saveRolePermission.getPermissionIdList().isEmpty()) {
            return RespWrapper.validateFailed("角色授权失败，菜单权限信息不存在！");
        }
        SysRoleDo sysRoleDo = sysRoleMapper.selectById(saveRolePermission.getRoleId());
        if (null == sysRoleDo) {
            return RespWrapper.validateFailed("角色授权失败，角色已不存在！");
        }
        int ret = sysRolePermissionMapper.deleteRolePermissionByRoleId(saveRolePermission.getRoleId());
        log.info("saveRolePermission , deleteByRoleId ret={}, saveRolePermission={}", ret, saveRolePermission);
        final Date now = Calendar.getInstance().getTime();
        final String roleId = saveRolePermission.getRoleId();
        final String createBy = Identify.getSessionUser().getRealName();
        List<SysRolePermissionDo> list = saveRolePermission.getPermissionIdList()
                .stream().map(permissionId -> SysRolePermissionDo.builder()
                        .id(String.valueOf(IdWorker.getId("sys_role_permission")))
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .createTime(now)
                        .createBy(createBy)
                        .build()).toList();
        // 大批量数据直接一次性插入有可能失败，拆分成100条为一个批次
        List<List<SysRolePermissionDo>> batchList = CollUtil.split(list, 100);
        for (int i = 0; i < batchList.size(); i++) {
            List<SysRolePermissionDo> doList = batchList.get(i);
            ret = sysRolePermissionMapper.batchInsert(doList);
            log.info("saveRolePermission , batchInsert ret={}, i={},doList={}", ret, i, doList);
        }
        // 故意抛出异常测试事务回滚
        //throw new RuntimeException("测试事务回滚");
        return RespWrapper.success(true);
    }

    @Override
    public RespWrapper<Boolean> deleteRole(String roleId) {
        log.info("deleteRole , roleId={}", roleId);
        if (StrUtil.isBlank(roleId)) {
            return RespWrapper.validateFailed("删除失败，角色id为空！");
        }
        SysRoleDo sysRoleDo = sysRoleMapper.selectById(roleId);
        if (null == sysRoleDo) {
            return RespWrapper.validateFailed("删除失败，角色不存在！");
        }
        // 判断下面有没有人
        List<SysUserRoleDo> list = sysUserRoleMapper.findUserRoleByRoleId(roleId);
        if (CollUtil.isNotEmpty(list)) {
            log.info("deleteRole, roleId={}, list={}", roleId, list);
            return RespWrapper.fail("删除失败，该角色下有用户！");
        }
        List<SysRolePermissionDo> rolePermissionDoList = sysRolePermissionMapper.selectList(
                new LambdaQueryWrapper<SysRolePermissionDo>()
                        .eq(SysRolePermissionDo::getRoleId, roleId)
        );
        if (CollUtil.isNotEmpty(rolePermissionDoList)) {
            int ret = sysRolePermissionMapper.delete(
                    new LambdaUpdateWrapper<SysRolePermissionDo>()
                            .eq(SysRolePermissionDo::getRoleId, roleId)
            );
            if (ret == 0) {
                log.error("deleteRole, sysRolePermissionMapper.delete fail, roleId={}", roleId);
                return RespWrapper.fail("删除失败！");
            }
        }
        if (sysRoleMapper.deleteById(roleId) == 0) {
            return RespWrapper.fail("删除失败！");
        }
        return RespWrapper.success(true);
    }

    @Override
    public RespWrapper<List<SysRole>> roleList() {
        log.info("roleList");
        List<SysRoleDo> list = sysRoleMapper.selectList(null);
        List<SysRole> sysRoleList = SysRoleConvert.INSTANCE.toDomain(list);
        return RespWrapper.success(sysRoleList);
    }
}
