package com.nutcracker.example.demo.util;

import cn.hutool.core.util.StrUtil;

/**
 * 手机号掩码工具类
 *
 * @author 胡桃夹子
 * @date 2021/11/17 16:36
 */
public class FackNoUtil {

    public static String getFackNo(String no) {
        if (StrUtil.isBlank(no) || no.length() < 5) {
            return StrUtil.EMPTY;
        }
        int fixedlength = 8;
        int fackLength = 4;
        String fackString = "****";
        int startsubindex = 0;
        if (no.length() < fixedlength) {
            startsubindex = fixedlength - no.length();
        } else if (no.length() == fixedlength) {
            startsubindex = 3;
        } else {
            startsubindex = no.length() - fixedlength;
        }
        if (no.length() <= fackLength + 1) {
            return no.charAt(0) + fackString;
        }
        return no.substring(0, startsubindex) + fackString + no.substring((startsubindex + fackLength));
    }
}
