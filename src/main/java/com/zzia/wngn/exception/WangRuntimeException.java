package com.zzia.wngn.exception;

/**
 * @Title 自定义运行期异常
 * @Author  wanggang
 * @Date 	2015年6月25日 下午10:33:58
 * @Version 1.0
 * @Description
 */
public class WangRuntimeException extends BaseRuntimeException{

	private static final long serialVersionUID = 1L;

	public WangRuntimeException(BaseRuntimeException cause) {
		super(cause);
	}

	public WangRuntimeException(IErrorCode errorCode, Object... params) {
		super(errorCode, params);
	}

	public WangRuntimeException(String message, IErrorCode errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

	public WangRuntimeException(String message) {
		super(message);
	}

}
