package com.nutcracker.example.demo.entity.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户对象
 *
 * @author 胡桃夹子
 * @date 2025/02/06 09:15:54
 */
@Schema(description = "用户对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -2617669111308455616L;

    @Schema(description = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "用户登录密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date createTime;

    @Schema(description = "创建人")
    private String createUserRealName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "更新人")
    private String updateUserRealName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleId;

    @Schema(description = "角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

}
