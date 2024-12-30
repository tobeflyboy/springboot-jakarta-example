package com.nutcracker.example.demo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * menu name vo
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:20
 */
@Data
@Builder
public class MenuNameVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 343753729931008468L;
    private String id;

    private String menuName;
}
