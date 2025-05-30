package com.nutcracker.example.demo.config.security;

import com.nutcracker.example.demo.constant.DemoConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * 安全配置
 *
 * @author 胡桃夹子
 * @date 2025/04/28 17:01:48
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("White list: {}", Arrays.toString(DemoConstants.WHITE_LIST));
        http
                // 禁用CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 跨域配置
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 无状态会话
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 异常情况处理
                .exceptionHandling(exception -> exception
                        // 未登录处理
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 权限不足处理
                        .accessDeniedHandler(accessDeniedHandler))
                // 权限规则
                .authorizeHttpRequests(auth -> {
                    // 打印白名单路径
                    log.debug("White list paths: {}", Arrays.toString(DemoConstants.WHITE_LIST));
                    auth
                            // 放行白名单
                            .requestMatchers(DemoConstants.WHITE_LIST).permitAll()
                            // 其他路径需认证
                            .anyRequest().authenticated();
                })
                // 添加 JWT 过滤器
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 所有域名
        config.setAllowedOrigins(List.of("*"));
        // 所有头
        config.setAllowedHeaders(List.of("*"));
        // 暴露的响应头
        config.setExposedHeaders(List.of("*"));
        // 预检请求缓存时间
        config.setMaxAge(3600L);
        // 所有方法
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}