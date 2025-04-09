package com.nutcracker.common.exception;

import java.io.Serial;

/**
 * 自定义异常类
 *
 * @author 胡桃夹子
 * @date 2020/2/21 18:08
 */
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5787303780395601031L;

    public BusinessException() {

    }

    public BusinessException(String errorCode) {
        super(errorCode);
    }

    public BusinessException(Throwable caused) {
        super(caused);
    }

    public BusinessException(String errorCode, String errorMsg) {
        super(errorCode + "|" + errorMsg);
    }

    public BusinessException(String errorCode, Throwable caused) {
        super(errorCode, caused);
    }

    public BusinessException(String errorCode, String errorMsg, Throwable caused) {
        super(errorCode + "|" + errorMsg, caused);
    }

    public String getErrorCode() {
        return resultInfo(getMessage(), 0);
    }

    public String getErrorMsg() {
        return resultInfo(getMessage(), 1);
    }


    private String resultInfo(String errorMessage, Integer index) {
        String result = errorMessage;
        if (errorMessage != null && !errorMessage.isEmpty()) {
            if (index == 0) {
                result = errorMessage.substring(0, errorMessage.indexOf("|"));
            } else {
                result = errorMessage.substring(errorMessage.lastIndexOf("|") + 1);
            }
        }
        return result;
    }
}
