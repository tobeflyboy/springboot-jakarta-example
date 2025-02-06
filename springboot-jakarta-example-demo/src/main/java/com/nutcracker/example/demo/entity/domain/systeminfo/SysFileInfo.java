package com.nutcracker.example.demo.entity.domain.systeminfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * sys file info
 *
 * @author 胡桃夹子
 * @date 2022/12/22 08:58
 */
@Data
public class SysFileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1188641657863592497L;
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}
