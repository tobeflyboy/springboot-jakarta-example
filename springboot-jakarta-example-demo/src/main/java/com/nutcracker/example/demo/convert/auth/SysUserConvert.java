package com.nutcracker.example.demo.convert.auth;

import com.nutcracker.example.demo.entity.dataobject.auth.SysUserDo;
import com.nutcracker.example.demo.entity.domain.auth.SysUser;

import java.util.List;

/**
 * 用户转换器
 *
 * @author wangxin4
 * @date 2025/02/06 10:57:22
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface SysUserConvert {

    SysUserConvert INSTANCE = org.mapstruct.factory.Mappers.getMapper(SysUserConvert.class);

    /**
     * domain转do
     *
     * @param user {@link SysUser}
     * @return {@link SysUserDo}
     */
    SysUserDo toDo(SysUser user);

    /**
     * domain转do
     *
     * @param list {@link List }<{@link SysUser }>
     * @return {@link List }<{@link SysUserDo }>
     */
    List<SysUserDo> toDo(List<SysUser> list);

    /**
     * do转domain
     *
     * @param userDo {@link SysUserDo}
     * @return {@link SysUser}
     */
    SysUser toDomain(SysUserDo userDo);

    /**
     * do转domain
     *
     * @param list {@link List }<{@link SysUserDo }>
     * @return {@link List }<{@link SysUser }>
     */
    List<SysUser> toDomain(List<SysUserDo> list);

}
