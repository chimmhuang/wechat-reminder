package com.github.chimmhuang.wechat.push.exception;

/**
 * @author Chimm Huang
 * @date 2022/8/29
 */
public class WechatTokenException extends RuntimeException {
    public WechatTokenException() {
    }

    public WechatTokenException(String message) {
        super(message);
    }

    public WechatTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatTokenException(Throwable cause) {
        super(cause);
    }

    public WechatTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
