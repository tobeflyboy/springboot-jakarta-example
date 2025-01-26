package com.nutcracker.example.demo.vo;

import com.nutcracker.example.demo.entity.vo.auth.SysPermissionVo;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * menu vo
 * @author 胡桃夹子
 * @date 2022/12/22 13:21
 */
@Data
@Builder
public class MenuVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -4791979608536111109L;

    private String name;

    private String icon;

    private String code;

    private List<SysPermissionVo> sysMenus;

}
