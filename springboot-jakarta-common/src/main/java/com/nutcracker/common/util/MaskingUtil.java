package com.nutcracker.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * 掩码工具类
 *
 * @author 胡桃夹子
 * @date 2021/11/17 16:36
 */
public class MaskingUtil {

    public static String getMaskingNo(String no) {
        if (StrUtil.isBlank(no) || no.length() < 5) {
            return StrUtil.EMPTY;
        }
        int length = 8;
        int maskingLength = 4;
        String masking = "****";
        int startSubIndex;
        if (no.length() < length) {
            startSubIndex = length - no.length();
        } else if (no.length() == length) {
            startSubIndex = 3;
        } else {
            startSubIndex = no.length() - length;
        }
        if (no.length() <= maskingLength + 1) {
            return no.charAt(0) + masking;
        }
        return no.substring(0, startSubIndex) + masking + no.substring((startSubIndex + maskingLength));
    }
}
