package com.nutcracker.example.demo.entity.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户dto
 *
 * @author wangxin4
 * @date 2025/02/06 09:15:54
 */
@Schema(description = "用户分页查询结果")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -2617669111308455616L;

    @Schema(description = "id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "账号", name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "姓名", name = "realName")
    private String realName;

    @Schema(description = "邮箱", name = "email")
    private String email;

    @Schema(description = "状态", name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "创建时间", name = "createTime", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建人", name = "createUserRealName")
    private String createUserRealName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    @Schema(description = "更新人", name = "updateUserRealName")
    private String updateUserRealName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "最后登录时间", name = "lastLoginTime")
    private LocalDateTime lastLoginTime;

}
