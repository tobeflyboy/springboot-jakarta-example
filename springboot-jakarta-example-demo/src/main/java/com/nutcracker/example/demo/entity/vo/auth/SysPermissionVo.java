package com.nutcracker.example.demo.entity.vo.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 权限vo
 *
 * @author 胡桃夹子
 * @date 2025/01/02 10:43:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPermissionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -2051933842290600230L;

    @Schema(description = "id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "菜单编码", name = "permission_code", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "permission_code", jdbcType = JdbcType.VARCHAR)
    private String permissionCode;

    @Schema(description = "菜单名称", name = "permission_name", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(value = "permission_name", jdbcType = JdbcType.VARCHAR)
    private String permissionName;

    @Schema(description = "父菜单编码", name = "parent_permission_code")
    @TableField(value = "parent_permission_code", jdbcType = JdbcType.VARCHAR)
    private String parentPermissionCode;

    @Schema(description = "菜单URL", name = "url")
    @TableField(value = "url", jdbcType = JdbcType.VARCHAR)
    private String url;

    @Schema(description = "菜单样式图标名称", name = "icon")
    @TableField(value = "icon", jdbcType = JdbcType.VARCHAR)
    private String icon;

    @Schema(description = "菜单是否显示", name = "hide")
    @TableField(value = "hide", jdbcType = JdbcType.INTEGER)
    private Integer hide;

    @Schema(description = "菜单级别，最多三级", name = "lev")
    @TableField(value = "lev", jdbcType = JdbcType.INTEGER)
    private Integer lev;

    @Schema(description = "子菜单", name = "children")
    @TableField(value = "children", jdbcType = JdbcType.INTEGER)
    private List<SysPermissionVo> children;
}
