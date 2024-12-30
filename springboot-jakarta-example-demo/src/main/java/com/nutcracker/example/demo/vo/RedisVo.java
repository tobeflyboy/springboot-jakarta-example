package com.nutcracker.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * redis vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 4695053389999040814L;
    private String key;

    private String value;

}
