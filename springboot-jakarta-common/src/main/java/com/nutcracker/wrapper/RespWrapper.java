package com.nutcracker.wrapper;

import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Response Wrapper
 *
 * @author 胡桃夹子
 * @date 2025/04/03 10:32:05
 */
@ToString
@Getter
public class RespWrapper<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 4311633334311958565L;

    // Getter 方法
    private final int code;
    private final String msg;
    private final T data;
    private final long timestamp;

    // 构造函数私有化
    private RespWrapper(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // 成功响应（无数据）
    public static <T> RespWrapper<T> success() {
        return new RespWrapper<>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), null);
    }

    // 成功响应（带数据）
    public static <T> RespWrapper<T> success(T data) {
        return new RespWrapper<>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg(), data);
    }

    // 成功响应（自定义消息）
    public static <T> RespWrapper<T> success(String msg, T data) {
        return new RespWrapper<>(RespCode.SUCCESS.getCode(), msg, data);
    }

    // 失败响应
    public static <T> RespWrapper<T> fail(RespCode code) {
        return new RespWrapper<>(code.getCode(), code.getMsg(), null);
    }

    // 失败响应（自定义消息）
    public static <T> RespWrapper<T> fail(String msg) {
        return new RespWrapper<>(RespCode.FAILED.getCode(), msg, null);
    }

    // 参数校验失败
    public static <T> RespWrapper<T> validateFailed(String msg) {
        return new RespWrapper<>(RespCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    // 分页响应
    public static <T> RespWrapper<PageRespWrapper<T>> pageSuccess(List<T> list, long total) {
        return RespWrapper.success(new PageRespWrapper<>(list, total));
    }

}