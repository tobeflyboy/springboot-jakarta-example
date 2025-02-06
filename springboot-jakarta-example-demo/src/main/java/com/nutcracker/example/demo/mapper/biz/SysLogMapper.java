package com.nutcracker.example.demo.mapper.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nutcracker.example.demo.entity.domain.systeminfo.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sys log mapper
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:17
 */
@Mapper
public interface SysLogMapper {

    List<SysLog> findSysLogPage(Page<SysLog> page);

}
