package com.nutcracker.example.demo.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysMenu;
import com.nutcracker.example.demo.vo.MenuNameVO;
import com.nutcracker.example.demo.vo.SysMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys menu mapper
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:18
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    //@Select("SELECT *  FROM sys_menu as m LEFT JOIN sys_menu_role as r ON m.id = r.menu_id WHERE m.is_show = '1' and r.role_id = #{roleId} ORDER BY m.menu_weight")
    List<SysMenu> findByRoleId(@Param("roleId") String roleId);


    //@Select("SELECT * FROM sys_menu WHERE menu_level = 1 ORDER BY menu_weight")
    IPage<SysMenu> findFirstMenu(Page<SysMenu> page);

    //@Select("SELECT * FROM sys_menu WHERE parent_id = #{parentId} ORDER BY menu_weight")
    List<SysMenu> findByParentId(@Param("parentId") String parentId);

    /**
     * 修改菜单
     *
     * @param sysMenu 菜单
     * @return 返回值
     */
    //@Update("update sys_menu set parent_id = #{parentId}, menu_name = #{menuName}, menu_code = #{menuCode}, menu_href = #{menuHref}, " +
    //        "menu_level = #{menuLevel}, menu_weight = #{menuWeight}, menu_icon = #{menuIcon}, is_show = #{isShow} where id = #{id}")
    int updateMenu(SysMenuVO sysMenu);

    /**
     * 添加菜单
     *
     * @param sysMenu 菜单
     * @return 返回值
     */
    //@Insert("insert into sys_menu (`id`,`parent_id`,`menu_name`,`menu_code`,`menu_href`,`menu_level`,`menu_weight`,`is_show`,`create_date`,`create_by`) values" +
    //        "(#{id}, #{parentId}, #{menuName}, #{menuCode}, #{menuHref}, #{menuLevel}, #{menuWeight}, ${isShow}, #{createDate}, #{createBy})")
    int addMenu(SysMenuVO sysMenu);

    /**
     * 查询菜单
     *
     * @param menuName 菜单名称
     * @param menuCode 菜单别名
     * @param menuHref 菜单链接
     * @return 返回值
     */
    //@Select({"<script>select * from sys_menu where menu_name = #{menuName} or menu_code = #{menuCode} " +
    //        "<when test=\"menuHref != null and menuHref != ''\"> or menu_href = #{menuHref}</when></script>"})
    SysMenu getByName(@Param("menuName") String menuName, @Param("menuCode") String menuCode, @Param("menuHref") String menuHref);


    /**
     * 根据id查询菜单
     *
     * @param id id
     * @return 菜单
     */
    //@Select("select * from sys_menu where id = #{id}")
    SysMenu getById(@Param("id") String id);

    /**
     * 获取一级菜单
     *
     * @return 一级菜单
     */
    //@Select("SELECT * FROM sys_menu WHERE menu_level = 1 ORDER BY menu_weight")
    List<SysMenu> getFirstMenu();

    /**
     * 获取二级菜单
     */
    //@Select("SELECT * FROM sys_menu WHERE menu_level = 2")
    List<SysMenu> getSecondMenu();

    /**
     * 根据角色id查询所有父级菜单id
     *
     * @param roleId 角色id
     * @return 父级菜单id
     */
    //@Select("SELECT id FROM sys_menu sm WHERE sm.menu_level = 1 and id in " +
    //        "(select mr.menu_id from sys_menu_role mr left join sys_menu m on mr.menu_id = m.id where mr.role_id = #{roleId})" +
    //        " ORDER BY menu_weight")
    List<String> getRoleMenu(@Param("roleId") String roleId);

    /**
     * 获取菜单层级
     *
     * @return 菜单登记
     */
    //@Select("select distinct menu_level from sys_menu order by menu_level asc")
    List<String> getMenuLevel();

    /**
     * 查询当前菜单的上级菜单
     *
     * @param menuLevel 上级菜单层级
     * @return 上级菜单名称
     */
    //@Select("select id, menu_name from sys_menu where menu_level = #{menuLevel} order by create_date")
    List<MenuNameVO> getPreviousMenu(@Param("menuLevel") String menuLevel);

    /**
     * 根据菜单名称查询菜单id
     *
     * @param menuNames 菜单名称
     * @return 菜单id
     */
    //@Select("select id from sys_menu where menu_name = #{menuNames}")
    String getByMenuName(@Param("menuNames") String menuNames);

    /**
     * 根据id删除菜单(若为一级菜单id删除其子菜单)
     *
     * @param id id
     * @return 返回值
     */
    //@Delete("delete from sys_menu where id = #{id} or parent_id = #{id}")
    int deleteMenuById(String id);
}
