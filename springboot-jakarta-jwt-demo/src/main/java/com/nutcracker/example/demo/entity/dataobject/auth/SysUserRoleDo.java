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
 * sys_user_role data object
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:30:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户角色表")
@TableName("sys_user_role")
public class SysUserRoleDo implements Serializable {

    @Serial
    private static final long serialVersionUID = -56720255622342923L;

    @Schema(description = "id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "用户id", name = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "user_id", jdbcType = JdbcType.VARCHAR)
    private String userId;

    @Schema(description = "角色id", name = "roleId", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "role_id", jdbcType = JdbcType.VARCHAR)
    private String roleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Schema(description = "创建时间", name = "createTime", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "create_time", jdbcType = JdbcType.VARCHAR, typeHandler = CustomDateTypeHandler.class)
    private Date createTime;

    @Schema(description = "创建人id", name = "create_by")
    @TableField(value = "create_by", jdbcType = JdbcType.VARCHAR)
    private String createBy;

}
