package com.basic.main.utils;

import com.basic.main.enume.CodeEnum;
import com.basic.main.dto.Result;

public class ResultUtil {

    public static <T> Result<T> getSuccessResult(T data) {
        return getResultByCodeEnum(CodeEnum.SUCCESS, data);
    }

    public static <T> Result<T> getFailResult(T data) {
        return getResultByCodeEnum(CodeEnum.FAIL, data);
    }

    public static <T> Result<T> getUnknownResult(T data) {
        return getResultByCodeEnum(CodeEnum.UNKNOWN, data);
    }

    public static <T> Result<T> getResultByCodeEnum(CodeEnum codeEnum, T data) {
        return getResult(codeEnum.getCode(), codeEnum.getMessage(), data);
    }

    public static <T> Result<T> getResult(int code, String message, T data) {
        return new Result<>(code, message, data);
    }
}
