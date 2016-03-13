package com.zzia.wngn.domain;

/**
 * @title 请求响应结果消息型
 * @author WangGang
 * @Date 2016年3月10日 下午6:57:11
 * @Version 2.0
 * @Description
 */
public class ResponseResultMessage extends ResponseResult {

    /** 原因 */
    public String reason;

    /** 结果对象 */
    public Object result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 初始化请求响应对象
     * 
     * @param status
     * @param message
     * @return
     */
    public ResponseResultMessage init(int status, String message) {
        super.status = status;
        super.message = message;
        return this;
    }

    /**
     * 初始化请求响应对象
     * 
     * @param status
     * @param message
     * @param code
     * @return
     */
    public ResponseResultMessage init(int status, String message, String code) {
        this.init(status, message);
        super.code = code;
        return this;
    }

    /**
     * 补全请求响应对象参数
     * 
     * @param count
     * @param total
     * @param result
     * @param pagination
     * @param params
     * @return
     */
    public ResponseResultMessage append(String reason, Object result) {
        this.reason = reason;
        this.result = result;
        return this;
    }

    public ResponseResultMessage() {
        super();
    }

    public ResponseResultMessage(int status, String message) {
        super(status, message);
    }

    public ResponseResultMessage(int status, String message, String code) {
        super(status, message, code);
    }

    public ResponseResultMessage(int status, String message, Object result) {
        super(status, message);
        this.result = result;
    }

    public ResponseResultMessage(int status, String message, String code, String reason, Object result) {
        super(status, message, code);
        this.reason = reason;
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseResultMessage [status=" + status + ", message=" + message + ", code=" + code + ", reason="
                + reason + ", result=" + result + "]";
    }

}
