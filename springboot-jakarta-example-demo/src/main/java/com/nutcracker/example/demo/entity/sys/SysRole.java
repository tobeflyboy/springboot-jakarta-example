package com.nutcracker.example.demo.entity.sys;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys role
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:01
 */
@Data
@Builder
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -1365213589385823771L;

    /**
     * 主键
     */
    private String id;

    /**
     * 权限名称
     */
    private String authority;

    /**
     * 权限描述
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    public SysRole() {

    }

    public SysRole(String id, String name, String authority, Date createTime) {
        this.id = id;
        this.name = name;
        this.authority = authority;
        this.createTime = createTime;
    }

}
