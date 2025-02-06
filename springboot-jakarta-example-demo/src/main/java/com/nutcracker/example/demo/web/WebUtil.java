package com.nutcracker.example.demo.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.entity.domain.systeminfo.SysLog;
import com.nutcracker.example.demo.entity.domain.auth.SysPermission;
import com.nutcracker.example.demo.util.IpInfoUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
     * 按登录用户设置会话
     *
     * @param request 请求
     * @param userVo  用户vo
     */
    public static void setSessionUser(HttpServletRequest request, SessionUser userVo) {
        request.getSession().setAttribute(DemoConstants.LOGIN_USER_SESSION_KEY, userVo);
    }

    /**
     * 获取登录用户
     *
     * @param request 请求
     * @return {@link SessionUser }
     */
    public static SessionUser getSessionUser(HttpServletRequest request) {
        return (SessionUser) request.getSession().getAttribute(DemoConstants.LOGIN_USER_SESSION_KEY);
    }

    /**
     * 获取所有子菜单url
     *
     * @param request 请求
     * @return {@link List }<{@link String }>
     */
    public static boolean hasPermission(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (isExcludePath(uri)) {
            return true;
        }
        String servletPath = StrUtil.replace(request.getServletPath(), "/", "");
        Object sessionUser = request.getSession().getAttribute(DemoConstants.LOGIN_USER_SESSION_KEY);
        if (!Objects.isNull(sessionUser)) {
            SessionUser userVo = (SessionUser) sessionUser;
            if (CollectionUtil.isNotEmpty(userVo.getPermissions())) {
                List<SysPermission> secondMenu = userVo.getPermissions().stream().filter(o -> Objects.nonNull(o.getChildren())).toList();
                List<String> urlList = secondMenu.stream().map(SysPermission::getUrl).toList();
                return urlList.contains(servletPath);
            }
        }
        return false;
    }

    /**
     * 判断请求路径是否属于排除路径
     */
    public static boolean isExcludePath(String uri) {
        for (String path : DemoConstants.WHILE_URL_LIST) {
            if (uri.matches(path.replaceAll("\\*", ".*"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取请求来源的ip地址
     *
     * @author wagnxin4
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.isEmpty() || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.isEmpty()) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
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
