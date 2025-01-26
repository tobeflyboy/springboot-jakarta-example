package com.nutcracker.example.demo.entity.dataobject.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sys_role_permission data object
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:26:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色资源关系表")
@TableName("sys_role_permission")
public class SysRolePermissionDo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7948507636703811294L;

    @Schema(description = "id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId("id")
    private String id;

    @Schema(description = "角色id", name = "role_id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("role_id")
    private String roleId;

    @Schema(description = "资源id", name = "permission_id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("permission_id")
    private String permissionId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "创建时间", name = "create_time", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "create_time", jdbcType = JdbcType.TIMESTAMP)
    private LocalDateTime createTime;

    @Schema(description = "创建人ID", name = "create_by")
    @TableField(value = "create_by", jdbcType = JdbcType.VARCHAR)
    private String createBy;

}
