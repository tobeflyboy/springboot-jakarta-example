package com.nutcracker.example.demo.vo;

import com.nutcracker.example.demo.entity.sys.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * role vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVO extends SysRole {

    @Serial
    private static final long serialVersionUID = 6698861871441246840L;
    private String[] ids;
}
