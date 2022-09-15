package com.github.chimmhuang.wechat.push.exception;

/**
 * @author Chimm Huang
 * @date 2022/8/29
 */
public class HttpSendException extends RuntimeException {

    public HttpSendException() {
    }

    public HttpSendException(String message) {
        super(message);
    }

    public HttpSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpSendException(Throwable cause) {
        super(cause);
    }

    public HttpSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
