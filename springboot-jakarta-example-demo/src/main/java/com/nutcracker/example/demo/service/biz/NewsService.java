package com.nutcracker.example.demo.service.biz;


import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.entity.dataobject.biz.NewsDo;

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
     * @param newsDo 新闻
     * @return boolean
     */
    boolean addNews(NewsDo newsDo);

    /**
     * 编辑新闻
     *
     * @param newsDo 新闻
     * @return boolean
     */
    boolean editNews(NewsDo newsDo);

    /**
     * 按id查找新闻
     *
     * @param newsId 新闻id
     * @return {@link NewsDo }
     */
    NewsDo findNewsById(String newsId);

    /**
     * 按关键字查找新闻
     *
     * @param keywords 关键字
     * @return {@link List }<{@link NewsDo }>
     */
    List<NewsDo> findNewsByKeywords(String keywords);

    /**
     * 按页面查找新闻
     *
     * @param pageNum  书籍页码
     * @param keywords 关键字
     * @return {@link PageInfo }<{@link NewsDo }>
     */
    PageInfo<NewsDo> findNewsByPage(Integer pageNum, String keywords);

    /**
     * 按标题查找新闻
     *
     * @param title 标题
     * @return {@link NewsDo }
     */
    NewsDo findNewsByTitle(String title);
}