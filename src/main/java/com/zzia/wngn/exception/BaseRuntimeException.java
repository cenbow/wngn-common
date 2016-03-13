package com.zzia.wngn.exception;

import java.util.regex.Matcher;

/**
 * @Title 运行期异常
 * @Author  wanggang
 * @Date 	2015年6月25日 下午10:10:44
 * @Version 1.0
 * @Description 运行期异常
 */
public class BaseRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/** 异常编码 */
	private IErrorCode errorCode;
	
	/**
	 * 构造函数：
	 * @param message 
	 */
	public BaseRuntimeException(String message){
    	super(message);
    }
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param errorCode 异常编码
	 * @param cause causeby
	 */
    public BaseRuntimeException(String message, IErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     * @param cause causeby
     */
    public BaseRuntimeException(BaseRuntimeException cause) {
        this(cause.getMessage(), cause.getErrorCode(), cause); 
    }

    /**
     * 构造函数
     * @param errorCode 异常编码
     * @param params 参数 error中的{}与参数个数相同
     */
    public BaseRuntimeException(IErrorCode errorCode, Object... params) {
    	this(format("{}: {}", errorCode.toString(),format(errorCode.getValue(), params)), errorCode, getCause(params));
    }
    
    
    public static Throwable getCause(Object... params) {
        Throwable throwable = null;
        if (params != null && params.length > 0 && params[params.length - 1] instanceof Throwable) {
            throwable = (Throwable) params[params.length - 1];
        }
        return throwable;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
    
    /**
	 * 字符串占位符"{}"替换,为了与日志logback占位符格式一致专门方法
	 * @param msgTemplate 带有占位符的字符串,占位符数量和替换参数数量一致
	 * @param params 替换占位符的数组值，如果替换参数比占位符数量多，占位符替换完之后的参数不再替换，参数少时，占位符会有空余。
	 * @return 替换完成的字符串
	 */
    public static String format(String msgTemplate, Object... params) {
    	for(int i=0;i<params.length;i++){
    		if(params[i]!=null){
    			String str = String.valueOf(params[i]) ;
    			str = Matcher.quoteReplacement(str) ;
	    		msgTemplate = msgTemplate.replaceFirst("\\{\\}", str);
    		}
        }
        return msgTemplate;
    }
	
	
}
