package com.nutcracker.example.demo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * AccessAuthHandlerInterceptor
 *
 * @author 胡桃夹子
 * @date 2022/12/23 08:25
 */
@Slf4j
@Component
public class AccessAuthHandlerInterceptor implements HandlerInterceptor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = StrUtil.replace(request.getServletPath(), "/", "");
        log.debug("preHandle servletPath={}", servletPath);
        //if (WebUtil.hasPermission(request)) {
        //    return true;
        //}
        //String contextPath = request.getContextPath();
        //log.info("preHandle {}/{}", contextPath, servletPath);
        //response.sendRedirect(contextPath + "/403");
        return true;
    }

}
