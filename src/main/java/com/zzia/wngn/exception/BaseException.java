package com.zzia.wngn.exception;

import java.util.regex.Matcher;

/**
 * @Title 自定义异常基类
 * @Author wanggang
 * @Date 2015年6月3日 下午11:24:51
 * @Version 1.0
 * @Description 项目中所有自定义异常的基类
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    /** 异常编码 */
    private IErrorCode errorCode;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, IErrorCode errorCode, Throwable cause) {
        super(format("{}:{}", errorCode.toString(), errorCode.getValue()), cause);
        this.errorCode = errorCode;
    }

    public BaseException(BaseException cause) {
        this(cause.getMessage(), cause.getErrorCode(), cause);
    }

    public BaseException(IErrorCode errorCode, Object... params) {
        this(format("{}:{}", errorCode.toString(), format(errorCode.getValue(), params)), errorCode, getCause(params));
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause, IErrorCode errorCode, Object... params) {
        this(format("{}:{}", errorCode.toString(), format(errorCode.getValue(), params)), errorCode, cause);
    }

    public BaseException(String message, Throwable cause, IErrorCode errorCode, Object... params) {
        this(format("{}:{}:{}", errorCode.toString(), format(errorCode.getValue(), params), message), cause);
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
     * 
     * @param msgTemplate
     *            带有占位符的字符串,占位符数量和替换参数数量一致
     * @param params
     *            替换占位符的数组值，如果替换参数比占位符数量多，占位符替换完之后的参数不再替换，参数少时，占位符会有空余。
     * @return 替换完成的字符串
     */
    public static String format(String msgTemplate, Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                String str = String.valueOf(params[i]);
                str = Matcher.quoteReplacement(str);
                msgTemplate = msgTemplate.replaceFirst("\\{\\}", str);
            }
        }
        return msgTemplate;
    }

}
