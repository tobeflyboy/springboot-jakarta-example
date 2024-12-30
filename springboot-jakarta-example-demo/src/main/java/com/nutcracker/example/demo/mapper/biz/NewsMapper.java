package com.nutcracker.example.demo.mapper.biz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nutcracker.example.demo.entity.biz.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * news mapper
 *
 * @author 胡桃夹子
 * @date 2022-12-30 17:56
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 根据关键字查询新闻
     *
     * @param keywords 关键字
     * @return 新闻数据
     */
    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    /**
     * 根据关键字分页查询新闻
     *
     * @param page     分页查询对象
     * @param keywords 关键字
     * @return 新闻数据
     */
    IPage<News> findNewsByPage(IPage<News> page, @Param("keywords") String keywords);

    /**
     * 更新新闻
     *
     * @param news 新闻对象，id为必传值
     * @return true=更新成功;false=更新失败;
     */
    int updateNews(News news);

}
