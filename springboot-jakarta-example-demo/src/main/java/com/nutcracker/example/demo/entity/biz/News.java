package com.nutcracker.example.demo.entity.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 新闻对象
 *
 * @author 胡桃夹子
 * @date 2022/3/15 14:10
 */
@Data
@TableName("t_news")
public class News implements Serializable {

    @Serial
    private static final long serialVersionUID = 3624947930970250778L;

    @TableId("id")
    private String id;

    /**
     * 新闻标题
     */
    @TableField("title")
    private String title;

    /**
     * 新闻内容
     */
    @TableField("description")
    private String description;

    /**
     * 新闻发生地址
     */
    @TableField("address")
    private String address;

    /**
     * 新闻发生时间
     */
    @TableField("news_time")
    private Date newsTime;

    /**
     * 新闻发布时间
     */
    @TableField("create_time")
    private Date createTime;

}
