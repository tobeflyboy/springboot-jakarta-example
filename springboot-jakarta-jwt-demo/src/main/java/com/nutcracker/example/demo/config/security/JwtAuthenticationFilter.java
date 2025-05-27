package com.nutcracker.example.demo.config.security;

import cn.hutool.core.util.StrUtil;
import com.nutcracker.common.domain.User;
import com.nutcracker.common.exception.BusinessException;
import com.nutcracker.example.demo.constant.DemoConstants;
import com.nutcracker.example.demo.service.auth.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, BusinessException {
        // 检查白名单
        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();
        String path = servletPath.startsWith(contextPath) ? servletPath.substring(contextPath.length()) : servletPath;
        log.info("Processing request path: {}", path);
        if (DemoConstants.isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头获取 Token
        final String token = request.getHeader(DemoConstants.TOKEN);
        if (token == null || !token.startsWith(DemoConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            log.info("Token 无效, uri={}", request.getRequestURI());
            throw new BusinessException("Token 无效");
        }

        User user = authService.parseToken(token);
        if (user == null) {
            // Token 无效，由 Spring Security 处理
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 加载用户并校验 Token 有效性
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (!StrUtil.equals(user.getUsername(), userDetails.getUsername())) {
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