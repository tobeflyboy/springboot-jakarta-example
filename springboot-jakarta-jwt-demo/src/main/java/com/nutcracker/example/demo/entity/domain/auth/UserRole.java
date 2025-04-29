package com.nutcracker.example.demo.entity.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色对象
 *
 * @author 胡桃夹子
 * @date 2025/02/06 09:15:54
 */
@Schema(description = "用户角色对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -7686199439208183608L;

    @Schema(description = "用户id", example = "1909164660242817025")
    private String userId;

    @Schema(description = "账号", example = "vincent")
    private String username;

    @Schema(description = "姓名", example = "胡桃夹子")
    private String realName;

    @Schema(description = "角色id", example = "1909164660242817025")
    private String roleId;

    @Schema(description = "角色编码", example = "admin")
    private String roleCode;

    @Schema(description = "角色", example = "管理员")
    private String roleName;

}
