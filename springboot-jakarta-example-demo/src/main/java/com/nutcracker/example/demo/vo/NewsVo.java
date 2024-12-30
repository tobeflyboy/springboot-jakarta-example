package com.nutcracker.example.demo.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * NewsVo
 *
 * @author 胡桃夹子
 * @date 2022-12-31 14:29
 */
@Data
public class NewsVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7399417585063950622L;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页显示条数
     */
    private Integer pageSize;

    /**
     * 查询关键字
     */
    private String keywords;
}
