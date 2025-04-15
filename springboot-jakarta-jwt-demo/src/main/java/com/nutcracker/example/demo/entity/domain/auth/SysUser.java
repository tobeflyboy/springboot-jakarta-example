package com.nutcracker.example.demo.entity.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nutcracker.example.demo.enums.SysUserStatusEnum;
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

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1909164660242817025")
    private String userId;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "vincent")
    private String username;

    @Schema(description = "登录密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String password;

    @Schema(description = "新登录密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String newPassword;

    @Schema(description = "姓名", example = "胡桃夹子")
    private String realName;

    @Schema(description = "邮箱", example = "vincent@demo.com")
    private String email;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-04-14T13:20:50.987+08:00", type = "string", format = "date-time")
    private Date createTime;

    @Schema(description = "创建人", example = "管理员")
    private String createUserRealName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "更新时间", example = "2025-04-14T13:20:50.987+08:00")
    private Date updateTime;

    @Schema(description = "更新人", example = "管理员")
    private String updateUserRealName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "最后登录时间", example = "2025-04-14T13:20:50.987+08:00")
    private Date lastLoginTime;

    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1909164660242817025")
    private String roleId;

    @Schema(description = "角色", requiredMode = Schema.RequiredMode.REQUIRED, example = "管理员")
    private String roleName;

    /**
     * 获取状态描述
     *
     * @return {@link String }
     */
    public String getStatusDesc() {
        return SysUserStatusEnum.of(this.status);
    }

}
