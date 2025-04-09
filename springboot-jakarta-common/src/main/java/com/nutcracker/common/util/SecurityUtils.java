package com.nutcracker.common.util;


import com.nutcracker.exception.BusinessException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * security utils
 *
 * @author 胡桃夹子
 * @date 2022/12/23 08:24
 */
public class SecurityUtils {

    // 默认的哈希算法名称
    private static final String ALGORITHM_NAME = "SHA-256";
    // 默认的哈希迭代次数
    private static final int HASH_ITERATIONS = 2;

    // 用于生成随机字符串的字符集
    private static final String ALPHABET_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    // 安全的随机数生成器
    private static final SecureRandom RANDOM = new SecureRandom();


    /**
     * 生成10个长度的随机字母字符串。
     *
     * @return 随机生成的字母字符串
     */
    public static String randomAlphabetic() {
        return randomAlphabetic(10);
    }

    /**
     * 生成指定长度的随机字母字符串。
     *
     * @param length 字符串的长度
     * @return 随机生成的字母字符串
     */
    public static String randomAlphabetic(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("长度不能为负");
        }
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET_CHARS.length());
            builder.append(ALPHABET_CHARS.charAt(index));
        }
        return builder.toString();
    }

    /**
     * 将字节数组转换为十六进制字符串。
     *
     * @param hash 哈希字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 加密密码。
     *
     * @param salt     盐值
     * @param pwd      明文密码
     * @param userName 用户名
     * @return 加密后的密码
     */
    public static String encryptPassword(String salt, String pwd, String userName) {
        try {
            // 使用用户 + 盐值生成盐字节
            byte[] saltBytes = (userName + salt).getBytes(StandardCharsets.UTF_8);
            byte[] hash = pwd.getBytes(StandardCharsets.UTF_8);

            // 初始化 MessageDigest
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_NAME);
            md.update(saltBytes);
            hash = md.digest(hash);

            // 执行多次迭代
            for (int i = 1; i < HASH_ITERATIONS; i++) {
                // MessageDigest 会自动重置
                hash = md.digest(hash);
            }

            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("加密算法未找到", e);
        }
    }

    //public static void main(String[] args) {
    //    String username = "admin";
    //    String salt = randomAlphabetic();
    //    String password = encryptPassword(salt, "admin", username);
    //    System.out.println(username);
    //    System.out.println(salt);
    //    System.out.println(password);
    //}
}
