package com.nutcracker.example.demo.mapper.biz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nutcracker.example.demo.entity.dataobject.biz.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 新闻mapper接口
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:33:12
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    List<News> findNewsByPage(@Param("keywords") String keywords);

    List<News> findNewsByTitle(@Param("title") String title);
}
