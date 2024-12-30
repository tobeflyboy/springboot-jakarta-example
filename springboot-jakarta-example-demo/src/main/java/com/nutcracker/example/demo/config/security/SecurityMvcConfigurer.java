package com.nutcracker.example.demo.config.security;

import com.nutcracker.example.demo.config.interceptor.AccessAuthHandlerInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SecurityMvcConfigurer
 *
 * @author 胡桃夹子
 * @date 2022/12/23 11:17
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class SecurityMvcConfigurer implements WebMvcConfigurer {

    //url访问权限验证拦截器
    private AccessAuthHandlerInterceptor accessAuthHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册过滤器
        registry.addInterceptor(accessAuthHandlerInterceptor).excludePathPatterns("/static/**").addPathPatterns("/**");
    }

    /**
     * 页面跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.debug("addViewControllers");
        registry.addViewController("/login").setViewName("login.html");
        registry.addViewController("/403").setViewName("403.html");
    }

}
