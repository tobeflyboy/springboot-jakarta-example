package com.nutcracker.example.demo.config;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;


/**
 * 上下文设置
 *
 * @author 胡桃夹子
 * @date 2022/2/9 10:49
 */
@Slf4j
@Component
public class ApplicationContext implements ServletContextAware {

    @Override
    public void setServletContext(ServletContext context) {
        String datetime = String.valueOf(System.currentTimeMillis());
        String contextPath = context.getContextPath();
        context.setAttribute("v_css", datetime);
        context.setAttribute("v_js", datetime);
        log.info("# version={} , contextPath={}", datetime, contextPath);
        context.setAttribute("ctx", contextPath);
    }

}
