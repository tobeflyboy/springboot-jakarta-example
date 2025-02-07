package com.nutcracker.example.demo.convert.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;

import java.util.List;

/**
 * 角色转换器
 *
 * @author wangxin4
 * @date 2025/02/06 10:57:22
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface SysPermissionConvert {

    SysPermissionConvert INSTANCE = org.mapstruct.factory.Mappers.getMapper(SysPermissionConvert.class);

    /**
     * domain转do
     *
     * @param role {@link SysPermission}
     * @return {@link SysPermissionDo}
     */
    SysPermissionDo toDo(SysPermission role);

    /**
     * domain转do
     *
     * @param list {@link List }<{@link SysPermission }>
     * @return {@link List }<{@link SysPermissionDo }>
     */
    List<SysPermissionDo> toDo(List<SysPermission> list);

    /**
     * do转domain
     *
     * @param roleDo {@link SysPermissionDo}
     * @return {@link SysPermission}
     */
    SysPermission toDomain(SysPermissionDo roleDo);

    /**
     * do转domain
     *
     * @param list {@link List }<{@link SysPermissionDo }>
     * @return {@link List }<{@link SysPermission }>
     */
    List<SysPermission> toDomain(List<SysPermissionDo> list);

}
