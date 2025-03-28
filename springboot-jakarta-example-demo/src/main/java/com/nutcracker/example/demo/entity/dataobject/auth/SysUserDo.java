package com.nutcracker.example.demo.entity.dataobject.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nutcracker.example.demo.mapper.CustomDateTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys_user data object
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:28:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户表")
@TableName("sys_user")
public class SysUserDo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1227495748732942139L;

    @Schema(description = "id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "用户登录账号", name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "username", jdbcType = JdbcType.VARCHAR)
    private String username;

    @Schema(description = "用户登录密码", name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "password", jdbcType = JdbcType.VARCHAR)
    private String password;

    @Schema(description = "salt码", name = "salt", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "salt", jdbcType = JdbcType.VARCHAR)
    private String salt;

    @Schema(description = "用户名称", name = "realName")
    @TableField(value = "real_name", jdbcType = JdbcType.VARCHAR)
    private String realName;

    @Schema(description = "用户邮箱地址", name = "email")
    @TableField(value = "email", jdbcType = JdbcType.VARCHAR)
    private String email;

    @Schema(description = "状态", name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "status", jdbcType = JdbcType.INTEGER)
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "创建时间", name = "createTime", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "create_time", jdbcType = JdbcType.VARCHAR, typeHandler = CustomDateTypeHandler.class)
    private Date createTime;

    @Schema(description = "创建人id", name = "create_by")
    @TableField(value = "create_by", jdbcType = JdbcType.VARCHAR)
    private String createBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "更新时间", name = "updateTime")
    @TableField(value = "update_time", jdbcType = JdbcType.VARCHAR, typeHandler = CustomDateTypeHandler.class)
    private Date updateTime;

    @Schema(description = "更新人id", name = "update_by")
    @TableField(value = "update_by", jdbcType = JdbcType.VARCHAR)
    private String updateBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "最后登录时间", name = "lastLoginTime")
    @TableField(value = "last_login_time", jdbcType = JdbcType.VARCHAR, typeHandler = CustomDateTypeHandler.class)
    private Date lastLoginTime;
}
