package com.basic.main.enume;

public enum CodeEnum {
    FAIL(0, "失败"), SUCCESS(1, "成功"),UNKNOWN(-1, "未知错误");

    private int code;

    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
