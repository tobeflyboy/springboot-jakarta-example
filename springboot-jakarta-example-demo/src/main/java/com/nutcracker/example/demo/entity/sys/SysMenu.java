package com.nutcracker.example.demo.entity.sys;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys menu
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:00
 */
@Data
@Builder
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = -5829579419121746472L;

    /**
     * 菜单主键
     */
    private String id;

    /**
     * 菜单父级
     */
    private String parentId;

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
     * 菜单创建时间
     */
    private Date createDate;

    /**
     * 菜单创建人
     */
    private String createBy;

    public SysMenu(String id, String parentId, String menuName, String menuCode, String menuHref, String menuIcon,
                   String menuLevel, int menuWeight, Boolean isShow, Date createDate, String createBy) {
        this.id = id;
        this.parentId = parentId;
        this.menuName = menuName;
        this.menuCode = menuCode;
        this.menuHref = menuHref;
        this.menuIcon = menuIcon;
        this.menuLevel = menuLevel;
        this.menuWeight = menuWeight;
        this.isShow = isShow;
        this.createDate = createDate;
        this.createBy = createBy;
    }
}
