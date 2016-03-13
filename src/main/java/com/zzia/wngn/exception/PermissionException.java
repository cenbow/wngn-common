package com.zzia.wngn.exception;

public class PermissionException extends BaseException {

    private static final long serialVersionUID = 1L;

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(BaseException cause) {
        super(cause);
    }

    public PermissionException(String message, IErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }

    public PermissionException(IErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause, IErrorCode errorCode, Object... params) {
        super(cause, errorCode, params);
    }

    public PermissionException(String message, Throwable cause, IErrorCode errorCode, Object... params) {
        super(message, cause, errorCode, params);
    }

}
