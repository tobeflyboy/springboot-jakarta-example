package com.nutcracker.example.demo.entity.systeminfo.vo;

import com.nutcracker.example.demo.entity.systeminfo.CpuInfo;
import com.nutcracker.example.demo.entity.systeminfo.HeapInfo;
import com.nutcracker.example.demo.entity.systeminfo.JvmInfo;
import com.nutcracker.example.demo.entity.systeminfo.MemInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 服务器动态信息vo
 *
 * @author 胡桃夹子
 * @date 2024/12/27 16:27:05
 */
@Data
@Builder
public class ServerDynamicInfoVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5445432631951411978L;

    /**
     * CPU信息
     */
    private CpuInfo cpuInfo;
    /**
     * 堆内存信息c
     */
    private HeapInfo heapInfo;
    /**
     * JVM信息
     */
    private JvmInfo jvmInfo;
    /**
     * 内存信息
     */
    private MemInfo memInfo;
}
