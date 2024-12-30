package com.nutcracker.example.demo.service.sys;

/**
 * redis service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:28
 */
public interface RedisService {

    /**
     * 查询验证码的值
     *
     * @param key 键
     * @return /
     */
    String getCodeVal(String key);

    /**
     * 保存验证码
     *
     * @param key 键
     * @param val 值
     */
    void saveCode(String key, Object val);

    /**
     * delete
     *
     * @param key 键
     */
    void delete(String key);

}
