package com.nutcracker.example.demo.service.sys.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysLog;
import com.nutcracker.example.demo.mapper.sys.SysLogMapper;
import com.nutcracker.example.demo.service.sys.SysLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * sys log service
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:37
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogDao;

    @Async
    @Override
    public void saveLoginLog(SysLog sysLog) {
        int ret = sysLogDao.insert(sysLog);
        log.debug(" sysLogDao.insert ret={}", ret);
    }

    @Override
    public IPage<SysLog> findSysLogPage(Page<SysLog> page) {
        return sysLogDao.findSysLogPage(page);
    }
}
