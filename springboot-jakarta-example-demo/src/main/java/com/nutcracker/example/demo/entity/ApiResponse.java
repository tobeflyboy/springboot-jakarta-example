package com.nutcracker.example.demo.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * api response
 *
 * @author 胡桃夹子
 * @date 2022/12/22 14:48
 */
@Data
public class ApiResponse<T extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 2032784277081974294L;
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getStandardMessage();
    }

    public static <T extends Serializable> ApiResponse<T> ofMessage(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T extends Serializable> ApiResponse<T> ofSuccess(T data) {
        return new ApiResponse<>(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(), data);
    }

    public static <T extends Serializable> ApiResponse<T> success(String message) {
        return new ApiResponse<>(Status.SUCCESS.getCode(), message, null);
    }

    public static <T extends Serializable> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(Status.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    public static <T extends Serializable> ApiResponse<T> ofStatus(Status status) {
        return new ApiResponse<>(status.getCode(), status.getStandardMessage(), null);
    }

    public enum Status {
        //一些状态码的描述
        SUCCESS(200, "OK"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error");
        private int code;
        private String standardMessage;

        Status(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }
}
