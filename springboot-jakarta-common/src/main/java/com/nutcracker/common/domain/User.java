package com.nutcracker.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 2573965649742628481L;

    @Schema(description = "用户ID", example = "182827785478483968")
    private String userId;

    @Schema(description = "账号", example = "test")
    private String username;

    @Schema(description = "姓名", example = "test")
    private String realName;

    @Schema(description = "角色id", example = "1907640285341380610")
    private String roleId;

    @Schema(description = "角色编码", example = "customer")
    private String roleCode;

    @Schema(description = "角色名称", example = "业务员")
    private String roleName;
}
