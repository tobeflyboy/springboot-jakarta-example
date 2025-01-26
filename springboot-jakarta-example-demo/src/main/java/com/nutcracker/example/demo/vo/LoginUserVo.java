package com.nutcracker.example.demo.vo;

import com.nutcracker.example.demo.entity.dataobject.auth.SysRoleDo;
import com.nutcracker.example.demo.entity.vo.auth.SysPermissionVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录用户vo
 *
 * @author 胡桃夹子@centaline.com.cn
 * @date 2024-04-11 17:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2573965649742628481L;

    @Schema(description = "用户ID", name = "id", example = "182827785478483968")
    private String id;

    @Schema(description = "账号", name = "name", example = "test")
    private String username;

    @Schema(description = "姓名", name = "nickName", example = "test")
    private String realName;

    @Schema(description = "资源", name = "username", example = "admin")
    private List<SysPermissionVo> permissions;

    @Schema(description = "角色", name = "realName", example = "CS王鑫")
    private SysRoleDo sysRoleDo;
}
