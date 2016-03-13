package com.zzia.wngn.domain;

/**
 * @title 请求响应结果对象
 * @author WangGang
 * @Date 2016年3月10日 下午6:37:47
 * @Version 2.0
 * @Description
 */
public class ResponseResult {

    /** 状态码 ：0 成功；1 失败； 2 异常；3 其他。 */
    public int status;

    /** 返回信息 */
    public String message;

    /** 编码 */
    public String code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseResult() {

    }

    public ResponseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseResult(int status, String message, String code) {
        this(status, message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseResult [status=" + status + ", message=" + message + ", code=" + code + "]";
    }

}
