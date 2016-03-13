package com.zzia.wngn.exception;

public class DatabaseException extends BaseException {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(BaseException cause) {
        super(cause);
    }

    public DatabaseException(String message, IErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }

    public DatabaseException(IErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause, IErrorCode errorCode, Object... params) {
        super(cause, errorCode, params);
    }

    public DatabaseException(String message, Throwable cause, IErrorCode errorCode, Object... params) {
        super(message, cause, errorCode, params);
    }

}
