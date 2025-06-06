package com.nutcracker.example.demo.service.auth;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nutcracker.common.exception.BusinessException;
import com.nutcracker.common.util.JSON;
import com.nutcracker.common.util.SecurityUtils;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysRolePermissionDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.dataobject.auth.SysUserRoleDo;
import com.nutcracker.example.demo.enums.SysPermissionEnum;
import com.nutcracker.example.demo.mapper.auth.SysPermissionMapper;
import com.nutcracker.example.demo.mapper.auth.SysRoleMapper;
import com.nutcracker.example.demo.mapper.auth.SysRolePermissionMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserMapper;
import com.nutcracker.example.demo.mapper.auth.SysUserRoleMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest(properties = {
        "spring.datasource.dynamic.datasource.auth.url=jdbc:sqlite:D:/workspace/github/springboot-jakarta-example/springboot-jakarta-jwt-demo/src/main/resources/db/auth.sqlite",
        "spring.datasource.dynamic.datasource.biz.url=jdbc:sqlite:D:/workspace/github/springboot-jakarta-example/springboot-jakarta-jwt-demo/src/main/resources/db/biz.sqlite"
})
public class InitTest {

    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 初始化auth示例数据
     */
    @Test
    public void initAuth() {
        sysPermissionMapper.delete(null);
        sysRoleMapper.delete(null);
        sysRolePermissionMapper.delete(null);
        sysUserMapper.delete(null);
        sysUserRoleMapper.delete(null);

        addSysPermission();
        addSysRole();
        addSysRolePermission();
        addSysUser();
        addSysUserRole();
    }

    public void addSysPermission() {
        try {
            List<SysPermissionDo> list = SysPermissionEnum.getPermissionList();
            log.info("\n{}", JSON.toJSONString(list));
            for (SysPermissionDo sysPermissionDo : list) {
                SysPermissionDo permissionDo = sysPermissionMapper.findByPermissionCode(sysPermissionDo.getPermissionCode());
                if (null == permissionDo) {
                    int isSuccess = sysPermissionMapper.insert(sysPermissionDo);
                    log.info("添加资源 {},{}", isSuccess, sysPermissionDo);
                } else {
                    log.error("资源已存在，{}", sysPermissionDo);
                }
            }
        } catch (Exception e) {
            log.error("addSysPermission fail", e);
        }
    }

    public void addSysRole() {
        try {
            SysRoleDo sysRoleDo = sysRoleMapper.findRoleByRoleCode(DemoConstants.ADMIN_ROLE_CODE);
            if (null == sysRoleDo) {
                sysRoleDo = SysRoleDo
                        .builder()
                        .id(String.valueOf(IdWorker.getId("sys_role")))
                        .roleCode(DemoConstants.ADMIN_ROLE_CODE)
                        .roleName("管理员")
                        .createTime(Calendar.getInstance().getTime())
                        .build();

                int isSuccess = sysRoleMapper.insert(sysRoleDo);
                log.info("添加角色 {},{}", isSuccess, sysRoleDo);
            } else {
                log.error("角色已存在，{}", sysRoleDo);
            }
        } catch (Exception e) {
            log.error("addSysRole fail", e);
        }
    }

    public void addSysRolePermission() {
        try {
            SysRoleDo sysRoleDo = sysRoleMapper.findRoleByRoleCode(DemoConstants.ADMIN_ROLE_CODE);
            if (sysRoleDo == null) {
                throw new BusinessException("未找到角色");
            }
            List<SysRolePermissionDo> list = new ArrayList<>();
            for (SysPermissionEnum sysPermissionEnum : SysPermissionEnum.values()) {
                SysPermissionDo sysPermissionDo = sysPermissionMapper.findByPermissionCode(sysPermissionEnum.getPermissionCode());
                if (sysPermissionDo == null) {
                    log.error("未找到资源，{}", sysPermissionEnum);
                    continue;
                }
                SysRolePermissionDo sysRolePermissionDo = sysRolePermissionMapper.findRolePermissionByRoleIdAndPermissionId(sysRoleDo.getId(), sysPermissionDo.getId());
                if (null == sysRolePermissionDo) {
                    sysRolePermissionDo = SysRolePermissionDo
                            .builder()
                            .id(String.valueOf(IdWorker.getId("sys_role_permission")))
                            .roleId(sysRoleDo.getId())
                            .permissionId(sysPermissionDo.getId())
                            .createTime(Calendar.getInstance().getTime())
                            .build();
                    list.add(sysRolePermissionDo);
                } else {
                    log.error("角色资源已存在，{}", sysRolePermissionDo);
                }
            }
            List<List<SysRolePermissionDo>> batchList = CollUtil.split(list, 100);
            for (int i = 0; i < batchList.size(); i++) {
                List<SysRolePermissionDo> doList = batchList.get(i);
                int ret = sysRolePermissionMapper.batchInsert(doList);
                log.info("saveRolePermission , batchInsert ret={}, i={},doList={}", ret, i, doList);
            }
        } catch (Exception e) {
            log.error("addSysRolePermission fail", e);
        }
    }

    public void addSysUser() {
        try {
            String username = DemoConstants.ADMIN_USERNAME;
            SysUserDo sysUserDo = sysUserMapper.findUserByUsername(username);
            if (null == sysUserDo) {
                String secret = "123456";
                String salt = SecurityUtils.randomAlphabetic();
                String password = SecurityUtils.encryptPassword(salt, secret, username);
                sysUserDo = SysUserDo
                        .builder()
                        .id(String.valueOf(IdWorker.getId("sys_user")))
                        .username(username)
                        .password(password)
                        .salt(salt)
                        .realName("管理员")
                        .email("admin@nutcracker.com")
                        .status(1)
                        .createTime(Calendar.getInstance().getTime())
                        .build();
                int isSuccess = sysUserMapper.insert(sysUserDo);
                log.info("添加用户 {},{}", isSuccess, sysUserDo);
            } else {
                log.error("用户已存在，{}", sysUserDo);
            }
        } catch (Exception e) {
            log.error("addSysUser fail", e);
        }
    }


    public void addSysUserRole() {
        try {
            String roleCode = DemoConstants.ADMIN_ROLE_CODE;
            String username = DemoConstants.ADMIN_USERNAME;
            SysUserRoleDo sysUserRoleDo = sysUserRoleMapper.findUserRoleByUsernameAndRoleCode(username, roleCode);
            if (null == sysUserRoleDo) {
                SysUserDo sysUserDo = sysUserMapper.findUserByUsername(username);
                if (sysUserDo == null) {
                    log.error("未找到用户，{}", username);
                    throw new BusinessException("未找到用户,username=" + username);
                }
                SysRoleDo sysRoleDo = sysRoleMapper.findRoleByRoleCode(roleCode);
                if (sysRoleDo == null) {
                    log.error("未找到角色，{}", roleCode);
                    throw new BusinessException("未找到角色,roleCode=" + roleCode);
                }
                sysUserRoleDo = SysUserRoleDo
                        .builder()
                        .id(String.valueOf(IdWorker.getId("sys_user_role")))
                        .userId(sysUserDo.getId())
                        .roleId(sysRoleDo.getId())
                        .createTime(Calendar.getInstance().getTime())
                        .build();
                int isSuccess = sysUserRoleMapper.insert(sysUserRoleDo);
                log.info("添加用户角色 {},{}", isSuccess, sysUserRoleDo);
            } else {
                log.error("用户角色已存在，{}", sysUserRoleDo);
            }
        } catch (Exception e) {
            log.error("addSysUserRole fail", e);
        }
    }

}
