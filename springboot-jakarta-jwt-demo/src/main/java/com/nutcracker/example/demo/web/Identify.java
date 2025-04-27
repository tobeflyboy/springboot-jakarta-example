package com.nutcracker.example.demo.web;

import com.nutcracker.common.domain.User;

/**
 * 用于存储用户信息
 *
 * @author 胡桃夹子
 * @date 2025/03/20 14:05:58
 */
public class Identify {

    private Identify() {

    }

    private static final ThreadLocal<User> SESSION_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 缓存当前登录人
     *
     * @param user 用户
     */
    public static void setSessionUser(User user) {
        SESSION_USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取当前用户
     *
     * @return {@link User }
     */
    public static User getSessionUser() {
        return SESSION_USER_THREAD_LOCAL.get();
    }

    /**
     * 清除当前用户
     */
    public static void clearSessionUser() {
        User currentUser = SESSION_USER_THREAD_LOCAL.get();
        if (currentUser != null) {
            SESSION_USER_THREAD_LOCAL.remove();
        }
    }

}
