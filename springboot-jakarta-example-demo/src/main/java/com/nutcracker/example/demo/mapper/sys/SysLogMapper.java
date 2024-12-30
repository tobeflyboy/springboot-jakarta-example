package com.nutcracker.example.demo.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.sys.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys log mapper
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:17
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    //@Select("SELECT * FROM sys_log ORDER BY create_date desc")
    IPage<SysLog> findSysLogPage(Page<SysLog> page);

}
