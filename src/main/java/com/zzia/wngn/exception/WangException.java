package com.zzia.wngn.exception;

/**
 * @Title 	自定义异常
 * @Author  wanggang
 * @Date 	2015年6月3日 下午11:28:04
 * @Version 1.0
 * @Description 自定义异常使用模版
 */
public class WangException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	public WangException(String message){
    	super(message);
    }
	
    public WangException(BaseException cause) {
		super(cause);
	}

	public WangException(String message, IErrorCode errorCode, Throwable cause) {
        super(message,errorCode, cause);
    }
    
    public WangException(IErrorCode errorCode, Object... params) {
    	super(errorCode, params);
    }
}
