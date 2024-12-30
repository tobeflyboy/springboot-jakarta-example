package com.nutcracker.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis utils
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:35
 */
@Component
public class RedisUtils {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    @SuppressWarnings("unchecked")
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key        缓存key
     * @param value      缓存value
     * @param expireTime 缓存失效时间
     */
    @SuppressWarnings("unchecked")
    public boolean set(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key 缓存key
     */
    @SuppressWarnings("unchecked")
    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 缓存key
     */
    @SuppressWarnings("unchecked")
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除对应的value
     *
     * @param key 缓存key
     */
    @SuppressWarnings("unchecked")
    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 模糊查询key
     *
     * @param key 模糊key
     */
    @SuppressWarnings("unchecked")
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @SuppressWarnings("unchecked")
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 缓存key
     */
    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    //=======================set========================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    @SuppressWarnings("unchecked")
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询，是否存在
     *
     * @param key   键
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    public boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     */
    @SuppressWarnings("unchecked")
    public long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存 设置缓存时间
     *
     * @param key    键
     * @param time   时间
     * @param values 值可以多个
     */
    @SuppressWarnings("unchecked")
    public long sSetAndTime(String key, long time, Object... values) {
        long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    @SuppressWarnings("unchecked")
    public long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    @SuppressWarnings("unchecked")
    public long setRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }
}
