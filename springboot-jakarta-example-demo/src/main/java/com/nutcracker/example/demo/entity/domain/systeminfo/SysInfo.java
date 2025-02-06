package com.nutcracker.example.demo.entity.domain.systeminfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * sys info
 *
 * @author 胡桃夹子
 * @date 2022/12/22 08:59
 */
@Data
public class SysInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8314084055172528006L;
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
