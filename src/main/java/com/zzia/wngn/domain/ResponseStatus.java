package com.zzia.wngn.domain;

/**
 * @title 请求返回的状态码
 * @author WangGang
 * @Date 2016年3月10日 下午6:49:06
 * @Version 2.0
 * @Description
 */
public enum ResponseStatus {

    OK(0, "ok"), FAIL(1, "fail"), ERROR(2, "error"), OTHER(3, "other");

    /** 状态码 */
    public int code;

    /** 状态默认的描述信息 */
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
