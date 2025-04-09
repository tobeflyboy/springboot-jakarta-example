package com.nutcracker.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 时间转换工具类
 *
 * @author 胡桃夹子
 * @date 2020-08-14 20:10
 */
public class TimeUtil {

    /**
     * 获取时间差，分钟
     *
     * @param time 毫秒值,执行开始时间
     * @return 时间差
     */
    public static BigDecimal getMinute(long time) {
        long diff = System.currentTimeMillis() - time;
        BigDecimal n1 = new BigDecimal(diff);
        BigDecimal n2 = new BigDecimal(60000);
        return n1.divide(n2, 4, RoundingMode.HALF_UP);
    }

    /**
     * 获取时间差，秒
     *
     * @param time 毫秒值,执行开始时间
     * @return 时间差
     */
    public static BigDecimal getSecond(long time) {
        long diff = System.currentTimeMillis() - time;
        BigDecimal n1 = new BigDecimal(diff);
        BigDecimal n2 = new BigDecimal(1000);
        return n1.divide(n2, 4, RoundingMode.HALF_UP);
    }
}
