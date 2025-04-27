package com.nutcracker.example.demo.entity.domain.auth;

import com.nutcracker.common.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户vo
 *
 * @author 胡桃夹子@centaline.com.cn
 * @date 2024-04-11 17:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 2573965649742628481L;

    @Schema(description = "token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3NDQyNjYwMjF9.KNDyfjv5zxe_oetmNvRpwIDVJFNM_Oyf5sVG9p9KrUQ")
    private String token;

    @Schema(description = "会话过期时间", example = "182827785478483968")
    private Date expiresAt;

    @Schema(description = "当前登录用户对象", example = "test")
    private User user;
}
