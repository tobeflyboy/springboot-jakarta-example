package com.nutcracker.example.demo.service.biz;


import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.dataobject.biz.News;

import java.util.List;

/**
 * 新闻接口类
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:33:39
 */
public interface NewsService {

    /**
     * 添加新闻
     *
     * @param news 新闻
     * @return boolean
     */
    boolean addNews(News news);

    /**
     * 编辑新闻
     *
     * @param news 新闻
     * @return boolean
     */
    boolean editNews(News news);

    /**
     * 按id查找新闻
     *
     * @param newsId 新闻id
     * @return {@link News }
     */
    News findNewsById(String newsId);

    /**
     * 按关键字查找新闻
     *
     * @param keywords 关键字
     * @return {@link List }<{@link News }>
     */
    List<News> findNewsByKeywords(String keywords);

    /**
     * 按页面查找新闻
     *
     * @param pageNum  书籍页码
     * @param keywords 关键字
     * @return {@link PageInfo }<{@link News }>
     */
    PageInfo<News> findNewsByPage(Integer pageNum, String keywords);

    /**
     * 按标题查找新闻
     *
     * @param title 标题
     * @return {@link News }
     */
    News findNewsByTitle(String title);
}