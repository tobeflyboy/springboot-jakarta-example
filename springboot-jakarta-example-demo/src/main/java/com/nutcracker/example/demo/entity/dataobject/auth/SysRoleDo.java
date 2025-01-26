package com.nutcracker.example.demo.entity.dataobject.auth;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * sys_role data object
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:25:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色表")
@TableName("sys_role")
public class SysRoleDo implements Serializable {

    @Serial
    private static final long serialVersionUID = -6982490361440305761L;

    @Schema(description = "角色id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "角色编码", name = "roleCode", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "role_code", jdbcType = JdbcType.VARCHAR)
    private String roleCode;

    @Schema(description = "角色名称", name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "role_name", jdbcType = JdbcType.VARCHAR)
    private String roleName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "创建时间", name = "create_time", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "create_time", jdbcType = JdbcType.TIMESTAMP)
    private LocalDateTime createTime;

    @Schema(description = "创建人ID", name = "create_by")
    @TableField(value = "create_by", jdbcType = JdbcType.VARCHAR)
    private String createBy;

}
