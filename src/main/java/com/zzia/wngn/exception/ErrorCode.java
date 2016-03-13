package com.zzia.wngn.exception;

/**
 * @Title 	异常编码
 * @Author  wanggang
 * @Date 	2015年6月3日 下午11:31:26
 * @Version 1.0
 * @Description
 */
public enum ErrorCode implements IErrorCode{

	E0000("自定义异常类型错误A:{}"),
    E0001("自定义异常类型错误B:{}"),
    E0002("自定义异常类型错误C:{}"),
    E00000000("{}:{}:数据库异常"),
    E00000001("PERMISSION:USER"),
    E00000002("PERMISSION:ROLE"),
    E00000003("PERMISSION:PERMISSION"),
    E00000004("PERMISSION:RESOURCE"),
    E00000005("PERMISSION:PARENTMENU"),
    E00000006("PERMISSION:SUBMENU"),
	E10000000("");

	/** 错误内容 */
    public String value; 

    private ErrorCode( String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getName() {
        return this.name();
    }
    
    public int getCode() {
        return Integer.parseInt(this.name().substring(1, this.name().length()));
    }
}
