package com.nutcracker.example.demo.entity.sys;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * sys user role
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:04
 */
@Data
@Builder
public class SysUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -6032539619366722404L;

    /**
     * 用户主键
     */
    private String userId;

    /**
     * 角色主键
     */
    private String roleId;

    public SysUserRole() {

    }

    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
