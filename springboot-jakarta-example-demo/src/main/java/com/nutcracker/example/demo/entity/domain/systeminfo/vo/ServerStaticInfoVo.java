package com.nutcracker.example.demo.entity.domain.systeminfo.vo;

import com.nutcracker.example.demo.entity.domain.systeminfo.SysFileInfo;
import com.nutcracker.example.demo.entity.domain.systeminfo.SysInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统信息vo
 *
 * @author 胡桃夹子
 * @date 2024/12/27 16:23:00
 */
@Data
@Builder
public class ServerStaticInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 5422456092715070849L;
    /**
     * 获取磁盘信息
     */
    List<SysFileInfo> sysFileInfo;

    /**
     * 获取服务器信息
     */
    private SysInfo sysInfo;
}
