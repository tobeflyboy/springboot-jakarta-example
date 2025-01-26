package com.nutcracker.example.demo.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Redisson Config
 *
 * @author 胡桃夹子
 * @since 2023-02-09 21:13
 */
@Slf4j
@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(@Value("${spring.redis.redisson.config}") String path) throws IOException {
        log.debug("redissonConfig={}", path);
        String config = StrUtil.replace(path, "classpath:", "");
        log.info("Redisson path={}", config);
        return Redisson.create(Config.fromYAML(new ClassPathResource(config).getInputStream()));
    }
}
