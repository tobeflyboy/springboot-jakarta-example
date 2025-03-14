package com.nutcracker.example.demo.convert.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.domain.auth.SysRole;

import java.util.List;

/**
 * 角色转换器
 *
 * @author 胡桃夹子
 * @date 2025/02/06 10:57:22
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = org.mapstruct.factory.Mappers.getMapper(SysRoleConvert.class);

    /**
     * domain转do
     *
     * @param role {@link SysRole}
     * @return {@link SysRoleDo}
     */
    SysRoleDo toDo(SysRole role);

    /**
     * domain转do
     *
     * @param list {@link List }<{@link SysRole }>
     * @return {@link List }<{@link SysRoleDo }>
     */
    List<SysRoleDo> toDo(List<SysRole> list);

    /**
     * do转domain
     *
     * @param roleDo {@link SysRoleDo}
     * @return {@link SysRole}
     */
    SysRole toDomain(SysRoleDo roleDo);

    /**
     * do转domain
     *
     * @param list {@link List }<{@link SysRoleDo }>
     * @return {@link List }<{@link SysRole }>
     */
    List<SysRole> toDomain(List<SysRoleDo> list);

}
