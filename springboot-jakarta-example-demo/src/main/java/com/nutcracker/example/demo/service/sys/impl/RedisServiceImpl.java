package com.nutcracker.example.demo.service.sys.impl;

import com.nutcracker.example.demo.service.sys.RedisService;
import com.nutcracker.example.demo.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:37
 */
@Service
@SuppressWarnings({"unchecked", "all"})
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisUtils redisUtils;

    @Value("${captcha.expiration}")
    private Long expiration;

    @Override
    public String getCodeVal(String key) {
        try {
            return Objects.requireNonNull(redisUtils.get(key)).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void saveCode(String key, Object val) {
        redisUtils.set(key, val, expiration, TimeUnit.MINUTES);
        /*redisTemplate.opsForValue().set(key,val);
        redisTemplate.expire(key,expiration, TimeUnit.MINUTES);*/
    }

    @Override
    public void delete(String key) {
        redisUtils.remove(key);
    }
}
