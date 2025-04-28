package com.nutcracker.example.demo.config.security;

import com.nutcracker.common.domain.User;
import com.nutcracker.common.util.JwtUtil;
import com.nutcracker.example.demo.constant.DemoConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * jwt身份验证过滤器
 *
 * @author 胡桃夹子
 * @date 2025/04/28 16:05:43
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 从请求头获取 Token
        String authHeader = request.getHeader("Token");
        if (authHeader == null || !authHeader.startsWith(DemoConstants.TOKEN_VALUE_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        // 去掉 "Bearer " 前缀
        User user = JwtUtil.parseToken(token, secret);
        if (user == null) {
            // Token 无效，由 Spring Security 处理
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 加载用户并校验 Token 有效性
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (!JwtUtil.validateToken(token, user.getUsername(), secret)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. 设置认证信息
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        userDetails.getAuthorities()
                );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}