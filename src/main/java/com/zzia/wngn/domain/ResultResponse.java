package com.zzia.wngn.domain;

/**
 * 
 * @title 请求响应返回结果数据
 * @author WangGang
 * @Date 2016年3月10日 下午7:30:56
 * @Version 1.0
 * @Description
 * @deprecated
 */
public class ResultResponse {

    public int status;
    public String message;
    public String reason;
    public Object result;

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

}
