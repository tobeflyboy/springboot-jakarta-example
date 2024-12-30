package com.nutcracker.example.demo.web.util;

import cn.hutool.core.util.IdUtil;
import com.nutcracker.example.demo.entity.sys.SysLog;
import com.nutcracker.example.demo.util.IpInfoUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Web选项
 *
 * @author 胡桃夹子
 * @date 2024/12/30 13:32:47
 */
@Slf4j
public class WebUtil {

    private WebUtil() {

    }

    /**
     * 是ajax
     *
     * @param request 请求
     * @return boolean
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 获取系统日志
     *
     * @param request HttpServletRequest
     * @param message 描述
     * @param name    登录账号
     * @return {@link SysLog }
     */
    public static SysLog getSysLog(HttpServletRequest request, String message, String name) {
        log.info("getSysLog message={},name={}", message, name);
        // 获取ip地址
        String ipAddr = IpInfoUtils.getIpAddr(request);
        // 获取ip来源
        String ipSource = IpInfoUtils.getipSource(ipAddr);
        //获取浏览器信息
        String browser = IpInfoUtils.getBrowser(request);
        // 获取系统名称
        String systemName = IpInfoUtils.getSystemName(request);
        return SysLog.builder()
                .username(name)
                .browserName(browser)
                .createDate(new Date())
                .id(IdUtil.randomUUID())
                .ipAddress(ipAddr)
                .ipSource(ipSource)
                .message(message)
                .systemName(systemName)
                .build();
    }
}
