package com.isnico.dsl.exception;

public class DslException extends RuntimeException{

    public DslException() {
    }

    public DslException(String message) {
        super(message);
    }

    public DslException(String message, Throwable cause) {
        super(message, cause);
    }

    public DslException(Throwable cause) {
        super(cause);
    }

    public DslException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
