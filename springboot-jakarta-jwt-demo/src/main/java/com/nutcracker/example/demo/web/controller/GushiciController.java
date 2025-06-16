package com.nutcracker.example.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 古诗词控制器
 *
 * @author 胡桃夹子
 * @date 2025/06/11 13:54:22
 */
@Slf4j
@Controller
public class GushiciController {

    @GetMapping("gushici/tengwanggexu")
    public String tengwanggexu() {
        log.debug("loading gushici/滕王阁序 ");
        return "gushici/滕王阁序";
    }

    @GetMapping("gushici/jiangjinjiu")
    public String jiangjinjiu() {
        log.debug("loading gushici/将进酒 ");
        return "gushici/将进酒";
    }

    @GetMapping("gushici/manjianghong")
    public String manjianghong() {
        log.debug("loading gushici/满江红 ");
        return "gushici/满江红·写怀";
    }

    @GetMapping("gushici/yueyanglouji")
    public String yueyanglouji() {
        log.debug("loading gushici/岳阳楼记");
        return "gushici/岳阳楼记";
    }

    @GetMapping("gushici/chushibiao")
    public String chushibiao() {
        log.debug("loading gushici/出师表");
        return "gushici/出师表";
    }

    @GetMapping("gushici/xiaoyaoyou")
    public String xiaoyaoyou() {
        log.debug("loading gushici/逍遥游");
        return "gushici/逍遥游";
    }

    @GetMapping("gushici/xiaoyaoyoujiexuan")
    public String xiaoyaoyoujiexuan() {
        log.debug("loading gushici/逍遥游节选");
        return "gushici/逍遥游节选";
    }

    @GetMapping("gushici/duangexing")
    public String duangexing() {
        log.debug("loading gushici/短歌行");
        return "gushici/短歌行";
    }

    @GetMapping("gushici/songdongyangmashengxu")
    public String songdongyangmashengxu() {
        log.debug("loading gushici/送东阳马生序");
        return "gushici/送东阳马生序";
    }

    @GetMapping("gushici/dingfengbo")
    public String dingfengbo() {
        log.debug("loading gushici/定风波");
        return "gushici/定风波";
    }

    @GetMapping("gushici/jiangchengziyimaozhengyueershiriyejimeng")
    public String jiangchengziyimaozhengyueershiriyejimeng() {
        log.debug("loading gushici/江城子·乙卯正月二十日夜记梦");
        return "gushici/江城子·乙卯正月二十日夜记梦";
    }

    @GetMapping("gushici/jiangchengzimizhouchulie")
    public String jiangchengzimizhouchulie() {
        log.debug("loading gushici/江城子·密州出猎");
        return "gushici/江城子·密州出猎";
    }

    @GetMapping("gushici/taohuaange")
    public String taohuaange() {
        log.debug("loading gushici/桃花庵歌");
        return "gushici/桃花庵歌";
    }

}
