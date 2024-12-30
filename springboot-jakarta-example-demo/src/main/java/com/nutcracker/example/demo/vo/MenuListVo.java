package com.nutcracker.example.demo.vo;

import com.nutcracker.example.demo.entity.sys.SysMenu;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * menu list vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:19
 */
@Data
@Builder
public class MenuListVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7591282348081566803L;
    /**
     * 菜单主键
     */
    private String id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单别名
     */
    private String menuCode;

    /**
     * 菜单链接
     */
    private String menuHref;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单级别
     */
    private String menuLevel;

    /**
     * 菜单权重
     */
    private int menuWeight;

    /**
     * 菜单是否显示
     */
    private Boolean isShow;

    /**
     * 子菜单
     */
    private List<SysMenu> children;

}
