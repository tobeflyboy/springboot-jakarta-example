package com.nutcracker.example.demo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * sys menu name vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuNameVO extends SysMenuVO {

    @Serial
    private static final long serialVersionUID = -6107656924413577956L;
    private String menuNames;

    public SysMenuNameVO() {

    }
}
