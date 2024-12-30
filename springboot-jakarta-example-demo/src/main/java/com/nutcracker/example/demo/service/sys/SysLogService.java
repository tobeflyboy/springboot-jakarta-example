package com.nutcracker.example.demo.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysLog;


/**
 * sys log service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:29
 */
public interface SysLogService {

    /**
     * 保存登录日志
     *
     * @param sysLog 系统日志
     */
    void saveLoginLog(SysLog sysLog);

    /**
     * 分页查询登录日志
     *
     * @param page 分页对象
     * @return 登录日志
     */
    IPage<SysLog> findSysLogPage(Page<SysLog> page);

}
