package com.nutcracker.example.demo.service.secret;


import com.nutcracker.example.demo.enums.SecretStrategyEnum;

import java.util.List;

/**
 * 加密解密操作
 *
 * @author 胡桃夹子
 * @date 2021-11-18 10:12
 */
public interface SecretService {

    /**
     * 加密
     *
     * @param secretStrategyEnum 加解密渠道枚举
     * @param param              明文号码
     * @return 密文号码
     */
    String encrypt(SecretStrategyEnum secretStrategyEnum, String param);

    /**
     * 解密
     *
     * @param secretStrategyEnum 加解密渠道枚举
     * @param param              密文号码
     * @return 明文号码
     */
    String decrypt(SecretStrategyEnum secretStrategyEnum, String param);

    /**
     * 批量加密操作
     *
     * @param secretStrategyEnum 加解密渠道枚举
     * @param list               密文号码
     * @return {@link String }
     */
    String encrypt(SecretStrategyEnum secretStrategyEnum, List<String> list);

    /**
     * 批量解密操作
     *
     * @param secretStrategyEnum 加解密渠道枚举
     * @param list               密文号码
     * @return {@link String }
     */
    String decrypt(SecretStrategyEnum secretStrategyEnum, List<String> list);

}
