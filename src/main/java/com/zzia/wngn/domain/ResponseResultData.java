package com.zzia.wngn.domain;

import java.util.List;
import java.util.Map;

/**
 * @title 请求响应结果数据型
 * @author WangGang
 * @Date 2016年3月10日 下午7:00:43
 * @Version 2.0
 * @Description
 */
public class ResponseResultData extends ResponseResult {

    /** 当前结果集数量 */
    private int count;

    /** 总数 */
    private long total;

    /** 结果集 */
    private List<Object> result;

    /** 分页 */
    private Pagination pagination;

    /** 查询参数 */
    private Map<String, Object> params;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 初始化请求响应对象
     * 
     * @param status
     * @param message
     * @return
     */
    public ResponseResultData init(int status, String message) {
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
    public ResponseResultData init(int status, String message, String code) {
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
    public ResponseResultData append(int count, long total, List<Object> result, Pagination pagination,
            Map<String, Object> params) {
        this.count = count;
        this.total = total;
        this.result = result;
        this.pagination = pagination;
        this.params = params;
        return this;
    }

    public ResponseResultData() {
        super();
    }

    public ResponseResultData(int status, String message) {
        super(status, message);
    }

    public ResponseResultData(int status, String message, String code) {
        super(status, message, code);
    }

    @Override
    public String toString() {
        return "ResponseResultData [status=" + status + ", message=" + message + ", code=" + code + ", count=" + count
                + ", total=" + total + ", result=" + result + ", pagination=" + pagination + ", params=" + params + "]";
    }

}
