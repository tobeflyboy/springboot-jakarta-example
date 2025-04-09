package com.nutcracker.example.demo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 加密或解密的类型枚举
 *
 * @author 胡桃夹子
 * @date 2021-11-17 18:21
 */
public enum SecretTypeEnum {

    /**
     * 加密
     */
    encrypt,

    /**
     * 解密
     */
    decrypt;


    private static final Map<String, SecretTypeEnum> ENUM_MAP = new HashMap<>();


    static {
        for (SecretTypeEnum enumNode : SecretTypeEnum.values()) {
            ENUM_MAP.put(enumNode.name(), enumNode);
        }
    }

    public static SecretTypeEnum getInstance(String code) {
        return ENUM_MAP.get(code);
    }

}
