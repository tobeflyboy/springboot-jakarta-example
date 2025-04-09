package com.nutcracker.common.wrapper;

import lombok.Getter;
import lombok.ToString;

/**
 * 结果代码
 *
 * @author 胡桃夹子
 * @date 2025/04/03 10:31:31
 */
@ToString
@Getter
public enum RespCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "资源不存在"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private final int code;
    private final String msg;

    RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}