package com.zzia.wngn.domain;

import java.util.List;
import java.util.Map;

/**
 * @title 请求响应返回结果数据
 * @author WangGang
 * @Date 2016年3月10日 下午7:27:03
 * @Version 1.0
 * @Description
 * @deprecated
 */
public class ReturnResponse {

    private Map<String, Object> params;
    private List<Object> datas;
    private Pagination pagination;
    private ResultResponse result;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public ResultResponse getResult() {
        return result;
    }

    public void setResult(ResultResponse result) {
        this.result = result;
    }

}
