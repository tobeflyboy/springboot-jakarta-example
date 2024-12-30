package com.nutcracker.example.demo.service.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nutcracker.example.demo.entity.biz.News;

import java.util.List;

/**
 * news service
 *
 * @author 胡桃夹子
 * @date 2022-12-30 18:00
 */
public interface NewsService {

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 新闻对象
     */
    News findNewsById(String id);

    /**
     * 根据关键字查询新闻
     *
     * @param keywords 关键字
     * @return 新闻数据
     */
    List<News> findNewsByKeywords(String keywords);

    /**
     * 查询所有用户
     *
     * @param page     当前页
     * @param pageSize 每页显示条数
     * @return 所有用户集合
     */
    IPage<News> getAll(Integer page, Integer pageSize, String keywords);

    /**
     * 删除新闻
     *
     * @param id 新闻ID
     * @return true=删除成功;false=删除失败;
     */
    boolean deleteNews(String id);

    /**
     * 更新新闻
     *
     * @param news 新闻对象，id为必传值
     * @return true=更新成功;false=更新失败;
     */
    boolean updateNews(News news);

    /**
     * 发布新闻
     *
     * @param news 新闻对象
     * @return true=成功;false=失败;
     */
    boolean addNews(News news);

}
