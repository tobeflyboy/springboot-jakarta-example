package com.nutcracker.example.demo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 加解密渠道枚举
 *
 * @author 胡桃夹子
 * @date 2021-11-17 18:21
 */
public enum SecretStrategyEnum {

    RSA,
    DES,
    DESede;

    private static final Map<String, SecretStrategyEnum> ENUM_MAP = new HashMap<>();


    static {
        for (SecretStrategyEnum enumNode : SecretStrategyEnum.values()) {
            ENUM_MAP.put(enumNode.name(), enumNode);
        }
    }

    public static SecretStrategyEnum getInstance(String code) {
        return ENUM_MAP.get(code);
    }

}
