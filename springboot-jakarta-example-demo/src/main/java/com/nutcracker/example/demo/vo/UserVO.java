package com.nutcracker.example.demo.vo;

import com.nutcracker.example.demo.entity.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * use vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends SysUser {

    @Serial
    private static final long serialVersionUID = -1465761641497463440L;
    private String userRole;

    public UserVO() {

    }

    public UserVO(String userRole) {
        this.userRole = userRole;
    }
}
