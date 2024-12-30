package com.nutcracker.example.demo.entity.sys;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * sys menu role
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:01
 */
@Data
@Builder
public class SysMenuRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 8500908793738945187L;

    /**
     * 菜单主键
     */
    private String menuId;

    /**
     * 角色主键
     */
    private String roleId;

    public SysMenuRole(String menuId, String roleId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }

    public SysMenuRole(String menuId) {
        this.menuId = menuId;
    }
}
