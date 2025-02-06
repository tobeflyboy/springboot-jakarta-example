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
 * sys_role domain
 *
 * @author 胡桃夹子
 * @date 2025/01/02 14:25:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色表")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -6982490361440305761L;

    @Schema(description = "角色id", name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "角色编码", name = "roleCode", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    @Schema(description = "角色名称", name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "创建时间", name = "create_time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建人", name = "createUserRealName")
    private String createUserRealName;

}
