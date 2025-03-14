package com.nutcracker.example.demo.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限隐藏枚举
 *
 * @author 胡桃夹子
 * @date 2025/02/07 10:39:11
 */
@Getter
@ToString
public enum SysPermissionHideEnum {

    SHOW(1, "显示"),
    HIDE(0, "隐藏");

    private final Integer code;
    private final String msg;

    private final static Map<Integer, SysPermissionHideEnum> MAP = new LinkedHashMap<>();

    private SysPermissionHideEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        for (SysPermissionHideEnum statusEnum : SysPermissionHideEnum.values()) {
            MAP.put(statusEnum.getCode(), statusEnum);
        }
    }

    public static String of(Integer status) {
        for (SysPermissionHideEnum statusEnum : SysPermissionHideEnum.values()) {
            if (statusEnum.getCode().equals(status)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

    public static Map<Integer, SysPermissionHideEnum> getHideMap() {
        return MAP;
    }
}
