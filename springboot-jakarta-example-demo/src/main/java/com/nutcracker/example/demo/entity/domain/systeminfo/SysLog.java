package com.nutcracker.example.demo.entity.domain.systeminfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys log
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:00
 */
@Data
@Builder
public class SysLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 8741031530653454214L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 关联用户
     */
    private String username;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 日志信息
     */
    private String message;

    /**
     * 浏览器名称
     */
    private String browserName;

    /**
     * 设备名称
     */
    private String systemName;

    /**
     * 创建时间
     */
    private Date createDate;
}
