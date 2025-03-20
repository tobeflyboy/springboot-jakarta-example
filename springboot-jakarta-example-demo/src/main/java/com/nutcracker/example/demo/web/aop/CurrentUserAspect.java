package com.nutcracker.example.demo.web.aop;

import com.nutcracker.example.demo.entity.domain.auth.SessionUser;
import com.nutcracker.example.demo.util.IpInfoUtils;
import com.nutcracker.example.demo.web.Identify;
import com.nutcracker.example.demo.web.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * 当前登录人切面
 *
 * @author 胡桃夹子
 * @date 2025/03/20 14:02:44
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class CurrentUserAspect {


    /**
     * 设置操作切入点 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.nutcracker.example.demo.web.controller..*.*(..))")
    public void currentUserPointCut() {
    }

    @Around("currentUserPointCut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) (requestAttributes != null ? requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST) : null);
            String uri = "", username = "", realName = "", ip = "", hostname = "", system = "", browser = "";

            if (null != request) {
                SessionUser user = WebUtil.getSessionUser(request);
                uri = request.getRequestURI();
                if (user != null) {
                    username = user.getUsername();
                    realName = user.getRealName();
                    Identify.setSessionUser(user);
                }
                ip = IpInfoUtils.getIpAddr(request);
                hostname = IpInfoUtils.getHostName();
                system = IpInfoUtils.getSystemName(request);
                browser = IpInfoUtils.getBrowser(request);
                //调用 proceed() 方法才会真正的执行实际被代理的方法
                MDC.put("uri", uri);
                MDC.put("username", username);
                MDC.put("realName", realName);
                MDC.put("ip", ip);
                MDC.put("browser", browser);
                MDC.put("hostname", hostname);
                MDC.put("system", system);
            }
            Object result = joinPoint.proceed();
            log.info("aroundMethod uri={},username={},realName={},ip={},hostname={},system={},browser={}", uri, username, realName, ip, hostname, system, browser);
            //方法结束后清除当前登录人、token、appId内存信息
            Identify.clearSessionUser();
            return result;
        } catch (Throwable throwable) {
            //发生异常清除当前登录人内存信息
            Identify.clearSessionUser();
            throw throwable;
        } finally {
            MDC.clear();
        }
    }
}

