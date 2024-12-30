package com.nutcracker.example.demo.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysMenu;
import com.nutcracker.example.demo.vo.MenuNameVO;
import com.nutcracker.example.demo.vo.MenuVo;
import com.nutcracker.example.demo.vo.SysMenuVO;

import java.util.List;

/**
 * sys menu service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:29
 */
public interface SysMenuService {

    List<MenuVo> getMenuByUser(String username);

    List<SysMenu> findMenuListByUser(String username);

    IPage<SysMenu> findFirstMenu(Page<SysMenu> page);

    List<SysMenu> findByParentId(String parentId);

    /**
     * 修改菜单
     *
     * @param sysMenu 菜单
     * @return 返回值
     */
    int updateMenu(SysMenuVO sysMenu);

    /**
     * 添加菜单
     *
     * @param sysMenu 菜单
     * @return 返回值
     */
    int addMenu(SysMenuVO sysMenu);

    /**
     * 查询菜单
     *
     * @param menuName 菜单名称
     * @param menuCode 菜单别名
     * @param menuHref 菜单链接
     * @return 返回值
     */
    SysMenu getByName(String menuName, String menuCode, String menuHref);

    /**
     * 根据id查询菜单
     *
     * @param id id
     * @return 菜单
     */
    SysMenu getById(String id);

    /**
     * 获取一级菜单
     *
     * @return 一级菜单
     */
    List<SysMenu> getFirstMenu();

    /**
     * 获取二级菜单
     */
    List<SysMenu> getSecondMenu();

    /**
     * 根据角色id查询所有菜单
     *
     * @param roleId 角色id
     * @return 菜单
     */
    List<String> getRoleMenu(String roleId);

    /**
     * 获取菜单层级
     *
     * @return 菜单登记
     */
    List<String> getMenuLevel();

    /**
     * 查询当前菜单的上级菜单
     *
     * @param menuLevel 上级菜单层级
     * @return 上级菜单名称
     */
    List<MenuNameVO> getPreviousMenu(String menuLevel);

    /**
     * 根据菜单名称查询菜单id
     *
     * @param menuNames 菜单名称
     * @return 菜单id
     */
    String getByMenuName(String menuNames);

    /**
     * 根据id删除菜单
     *
     * @param id id
     * @return 返回值
     */
    int deleteMenuById(String id);
}
