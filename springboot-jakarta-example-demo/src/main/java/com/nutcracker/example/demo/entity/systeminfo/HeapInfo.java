package com.nutcracker.example.demo.entity.systeminfo;

import cn.hutool.core.util.NumberUtil;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * heap info
 *
 * @author 胡桃夹子
 * @date 2022/12/22 08:57
 */
@Setter
public class HeapInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 3480288875627883912L;
    /**
     * 初始化
     */
    private double heapInit;

    /**
     * 堆最大
     */
    private double heapMax;

    /**
     * 堆已用
     */
    private double heapUsed;

    /**
     * 堆可用
     */
    private double heapCommitted;

    public double getHeapInit() {
        return NumberUtil.div(heapInit, (1024 * 1024), 2);
    }

    public double getHeapMax() {
        return NumberUtil.div(heapMax, (1024 * 1024), 2);
    }

    public double getHeapUsed() {
        return NumberUtil.div(heapUsed, (1024 * 1024), 2);
    }

    public double getHeapCommitted() {
        return NumberUtil.div(heapCommitted, (1024 * 1024), 2);
    }
}
