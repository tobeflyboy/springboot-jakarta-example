package com.nutcracker.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * image result
 *
 * @author 胡桃夹子
 * @date 2022/12/22 13:19
 */
@Data
@AllArgsConstructor
public class ImgResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -252832883068779354L;

    private String img;

    private String uuid;
}
