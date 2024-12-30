package com.nutcracker.example.demo.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys menu vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:22
 */
@Data
public class SysMenuVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3913595621304206815L;

    /**
     * 菜单主键
     */
    public String id;

    /**
     * 菜单父级
     */
    public String parentId;

    /**
     * 菜单名称
     */
    public String menuName;

    /**
     * 菜单别名
     */
    public String menuCode;

    /**
     * 菜单链接
     */
    public String menuHref;

    /**
     * 菜单图标
     */
    public String menuIcon;

    /**
     * 菜单级别
     */
    public String menuLevel;

    /**
     * 菜单权重
     */
    public String menuWeight;

    /**
     * 菜单是否显示
     */
    public String isShow;

    /**
     * 菜单创建时间
     */
    public Date createDate;

    /**
     * 菜单创建人
     */
    public String createBy;

    public SysMenuVO() {

    }

    public SysMenuVO(String id, String parentId, String menuName, String menuCode, String menuHref, String menuIcon,
                     String menuLevel, String menuWeight, String isShow, Date createDate, String createBy) {
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