package com.nutcracker.example.demo.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户状态枚举
 *
 * @author 胡桃夹子
 * @date 2025/02/06 09:10:05
 */
@Getter
@ToString
public enum SysUserStatusEnum {

    VALID(1, "有效"),
    INVALID(0, "无效");

    private final Integer code;
    private final String msg;

    private final static Map<Integer, SysUserStatusEnum> MAP = new LinkedHashMap<>();

    private SysUserStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        for (SysUserStatusEnum statusEnum : SysUserStatusEnum.values()) {
            MAP.put(statusEnum.getCode(), statusEnum);
        }
    }

    public static String of(Integer status) {
        for (SysUserStatusEnum statusEnum : SysUserStatusEnum.values()) {
            if (statusEnum.getCode().equals(status)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

    public static Map<Integer, SysUserStatusEnum> getStatusMap() {
        return MAP;
    }
}
