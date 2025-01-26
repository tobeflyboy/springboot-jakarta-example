package com.nutcracker.example.demo.service.secret.impl;

import com.nutcracker.example.demo.enums.SecretStrategyEnum;
import com.nutcracker.example.demo.service.secret.SecretService;
import com.nutcracker.example.demo.strategy.StrategyFactory;
import com.nutcracker.example.demo.strategy.secret.BaseStrategy;
import com.nutcracker.example.demo.util.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加密解密操作
 *
 * @author 胡桃夹子
 * @date 2021-11-18 10:17
 */
@Slf4j
@Service
public class SecretServiceImpl implements SecretService {

    @Resource
    private StrategyFactory strategyFactory;

    @Override
    public String encrypt(SecretStrategyEnum secretStrategyEnum, String param) {
        BaseStrategy strategy = strategyFactory.getSecretStrategy(secretStrategyEnum);
        if (null != strategy) {
            return strategy.encrypt(param);
        }
        log.error("encrypt fail, {}, {}", secretStrategyEnum, param);
        return null;
    }

    @Override
    public String decrypt(SecretStrategyEnum secretStrategyEnum, String param) {
        BaseStrategy strategy = strategyFactory.getSecretStrategy(secretStrategyEnum);
        if (null != strategy) {
            return strategy.decrypt(param);
        }
        log.error("decrypt fail, {}, {}", secretStrategyEnum, param);
        return null;
    }

    @Override
    public String encrypt(SecretStrategyEnum secretStrategyEnum, List<String> list) {
        BaseStrategy strategy = strategyFactory.getSecretStrategy(secretStrategyEnum);
        if (null != strategy) {
            return strategy.execute(list, true);
        }
        log.error("encrypt list fail, {}, {}", secretStrategyEnum, JSON.toJSONString(list));
        return null;
    }

    @Override
    public String decrypt(SecretStrategyEnum secretStrategyEnum, List<String> list) {
        BaseStrategy strategy = strategyFactory.getSecretStrategy(secretStrategyEnum);
        if (null != strategy) {
            return strategy.execute(list, false);
        }
        log.error("decrypt list fail, {}, {}", secretStrategyEnum, JSON.toJSONString(list));
        return null;
    }
}
