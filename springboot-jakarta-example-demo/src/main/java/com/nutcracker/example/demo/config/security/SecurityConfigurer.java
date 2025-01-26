package com.nutcracker.example.demo.config.security;

import com.nutcracker.example.demo.config.security.handler.AuthenticationFailureHandler;
import com.nutcracker.example.demo.config.security.handler.AuthenticationSuccessHandler;
import com.nutcracker.example.demo.config.security.handler.CustomLogoutSuccessHandler;
import com.nutcracker.example.demo.constant.DemoConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * SecurityConfigurer
 *
 * @author 胡桃夹子
 * @date 2022/12/23 11:04
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    /**
     * 最大登录数
     */
    @Value("${security.max-session}")
    private Integer maxSession;

    /**
     * 超出最大登录数，是否阻止登录
     */
    @Value("${security.prevents-login}")
    private Boolean preventsLogin;

    @Resource
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Resource
    private CustomInvalidSessionStrategy customInvalidSessionStrategy;

    @Resource
    private CustomExpiredSessionStrategy customExpiredSessionStrategy;

    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 放行接口
                .authorizeHttpRequests(authorize -> authorize
                        // 放行接口
                        .requestMatchers(DemoConstants.WHILE_URL_LIST)
                        .permitAll()
                        .anyRequest().authenticated()
                )
                // 其余的都需要权限校验
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                )
                // 防跨站请求伪造
                .csrf(AbstractHttpConfigurer::disable)
                // 表单登录配置
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl(DemoConstants.LOGIN_URL)
                        .loginPage(DemoConstants.LOGIN_URL)
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                        .permitAll()
                )
                // 禁用CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 允许iframe嵌入，解决 in a frame because it set 'X-Frame-Options' to 'deny'报错。
                .headers(headers -> headers
                        .frameOptions(Customizer.withDefaults())
                )
                // 登出配置
                .logout(logout -> logout
                        .logoutUrl(DemoConstants.LOGOUT_URL)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                // 会话管理
                .sessionManagement(sessionManagement -> sessionManagement
                        // 失效处理
                        .invalidSessionStrategy(customInvalidSessionStrategy)
                        // 同一账号同时允许多个设备在线
                        .maximumSessions(maxSession)
                        // 新用户挤走前用户
                        .maxSessionsPreventsLogin(preventsLogin)
                        // 超时处理
                        .expiredSessionStrategy(customExpiredSessionStrategy)
                        .sessionRegistry(sessionRegistry)
                )
                // 校验用户信息
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }


    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许任何域名使用
        config.addAllowedOrigin("*");
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // 允许任何方法（post、get等）
        config.addAllowedMethod("*");
        // 允许任何头
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        // 设置CORS filter的顺序，-1表示在Spring Security filter之后执行
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(-1);
        return bean;
    }
}
