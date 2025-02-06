package com.nutcracker.example.demo.entity.domain.systeminfo;

import cn.hutool.core.util.NumberUtil;
import lombok.Setter;

import java.io.Serializable;

/**
 * no heap info
 *
 * @author 胡桃夹子
 * @date 2022/12/22 08:58
 */
@Setter
public class NoHeapInfo implements Serializable {

    /**
     * 初始化
     */
    private double noHeapInit;

    /**
     * 堆最大
     */
    private double noHeapMax;

    /**
     * 堆已用
     */
    private double noHeapUsed;

    /**
     * 堆可用
     */
    private double noHeapCommitted;

    public double getNoHeapInit() {
        return NumberUtil.div(noHeapInit, (1024 * 1024), 2);
    }

    public double getNoHeapMax() {
        return NumberUtil.div(noHeapMax, (1024 * 1024), 2);
    }

    public double getNoHeapUsed() {
        return NumberUtil.div(noHeapUsed, (1024 * 1024), 2);
    }

    public double getNoHeapCommitted() {
        return NumberUtil.div(noHeapCommitted, (1024 * 1024), 2);
    }
}
