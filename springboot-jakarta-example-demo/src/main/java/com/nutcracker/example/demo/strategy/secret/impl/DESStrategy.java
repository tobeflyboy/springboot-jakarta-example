package com.nutcracker.example.demo.strategy.secret.impl;

import com.nutcracker.example.demo.enums.SecretStrategyEnum;
import com.nutcracker.example.demo.strategy.secret.BaseStrategy;
import com.nutcracker.example.demo.util.secret.DesUtil;
import org.springframework.stereotype.Component;

/**
 * DES加解密接口
 *
 * @author 胡桃夹子
 * @date 2021/11/17 18:04
 */
@Component
public class DESStrategy extends BaseStrategy {

    @Override
    public SecretStrategyEnum getSecretStrategyEnum() {
        return SecretStrategyEnum.DES;
    }

    @Override
    public String encrypt(String param) {
        return DesUtil.encrypt(param);
    }

    @Override
    public String decrypt(String param) {
        return DesUtil.decrypt(param);
    }
}
