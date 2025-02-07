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
public class SysPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -2051933842290600230L;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "菜单资源编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String permissionCode;

    @Schema(description = "菜单资源名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String permissionName;

    @Schema(description = "父菜单资源编码")
    private String parentPermissionCode;

    @Schema(description = "父菜单资源名称")
    private String parentPermissionName;

    @Schema(description = "菜单URL")
    private String url;

    @Schema(description = "菜单icon图标")
    private String icon;

    @Schema(description = "菜单是否显示")
    private Integer hide;

    @Schema(description = "菜单级别，最多三级")
    private Integer lev;

    @Schema(description = "排序")
    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建人", name = "createUserRealName")
    private String createUserRealName;

    @Schema(description = "子菜单")
    private List<SysPermission> children;
}
