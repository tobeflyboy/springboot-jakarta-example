package com.nutcracker.example.demo.strategy;

import com.nutcracker.example.demo.enums.SecretStrategyEnum;
import com.nutcracker.example.demo.strategy.secret.BaseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略工厂
 *
 * @author 胡桃夹子
 * @date 2021-11-18 09:24
 */
@Slf4j
@Service
public class StrategyFactory {

    private final Map<String, BaseStrategy> secretStrategyMap = new ConcurrentHashMap<>();

    /**
     * 注册策略
     *
     * @param strategy 策略
     */
    public void registerSecretStrategy(BaseStrategy strategy) {
        SecretStrategyEnum secretStrategyEnum = strategy.getSecretStrategyEnum();
        if (null != secretStrategyEnum) {
            log.info("# register strategy, {}", strategy.getClass().getName());
            secretStrategyMap.put(secretStrategyEnum.name(), strategy);
        }
    }


    /**
     * 获取策略
     *
     * @param secretStrategyEnum 加解密渠道枚举
     * @return 处理器
     */
    public BaseStrategy getSecretStrategy(SecretStrategyEnum secretStrategyEnum) {
        BaseStrategy strategy = null;
        if (null != secretStrategyEnum) {
            strategy = secretStrategyMap.get(secretStrategyEnum.name());
        } else {
            log.warn("# not fund strategy, {}", secretStrategyEnum);
        }
        return strategy;
    }
}
