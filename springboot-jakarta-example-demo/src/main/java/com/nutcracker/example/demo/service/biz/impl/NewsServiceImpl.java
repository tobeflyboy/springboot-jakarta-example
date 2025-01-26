package com.nutcracker.example.demo.service.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.constant.DataSourceTagger;
import com.nutcracker.example.demo.entity.dataobject.biz.News;
import com.nutcracker.example.demo.mapper.biz.NewsMapper;
import com.nutcracker.example.demo.service.biz.NewsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * 新闻接口实现类
 *
 * @author 胡桃夹子
 * @date 2025/01/02 15:47:32
 */
@SuppressWarnings("resource")
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Transactional
    @Override
    public boolean addNews(News news) {
        if (news != null) {
            news.setId(String.valueOf(IdWorker.getId("t_news")));
            news.setCreateTime(Calendar.getInstance().getTime());
            int flag = newsMapper.insert(news);
            return flag == 1;
        } else {
            return false;
        }
    }

    @Override
    public boolean editNews(News news) {
        if (news != null && StrUtil.isNotBlank(news.getId())) {
            int flag = newsMapper.updateById(news);
            return flag == 1;
        } else {
            return false;
        }
    }

    @Override
    public News findNewsById(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return newsMapper.selectById(id);

    }

    // 默认数据库
    @Override
    public List<News> findNewsByKeywords(String keywords) {
        log.info("# 查询默认数据库");
        return newsMapper.findNewsByKeywords(keywords);
    }

    @Override
    public PageInfo<News> findNewsByPage(Integer pageNum, String keywords) {
        // request: url?pageNum=1&pageSize=10
        // 支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
        if (pageNum == null) {
            pageNum = 1;
        }
        // 用PageInfo对结果进行包装
        PageInfo<News> page = PageHelper.startPage(pageNum, DemoConstants.PAGE_SIZE).doSelectPageInfo(()-> newsMapper.findNewsByPage(keywords));
        // 测试PageInfo全部属性
        // PageInfo包含了非常全面的分页属性
        log.info("# 查询默认数据库 page.toString()={}", page);
        return page;
    }

    @Override
    @DS(DataSourceTagger.BIZ)
    public News findNewsByTitle(String title) {
        // 从数据库1当中查询
        if (StrUtil.isBlank(title)) {
            return null;
        }
        List<News> list = newsMapper.findNewsByTitle(title);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.iterator().next();
    }

}
