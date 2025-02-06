package com.nutcracker.example.demo.entity.domain.systeminfo;

import cn.hutool.core.util.NumberUtil;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Memory info
 *
 * @author 胡桃夹子
 * @date 2022/12/22 08:57
 */
@Setter
public class MemInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1599941501050431287L;
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    /**
     * 使用率
     */

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }


    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}
