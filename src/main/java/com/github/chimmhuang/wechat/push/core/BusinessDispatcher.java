package com.github.chimmhuang.wechat.push.core;

import com.github.chimmhuang.wechat.push.handler.WechatTokenHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 核心执行流程
 *
 * @author Chimm Huang
 * @date 2022/9/15
 */
@Component
public class BusinessDispatcher {
    private static final Logger log = LoggerFactory.getLogger(BusinessDispatcher.class);
    public BusinessDispatcher(WechatTokenHandler wechatTokenHandler) {
        this.wechatTokenHandler = wechatTokenHandler;
    }

    private final WechatTokenHandler wechatTokenHandler;

    @PostConstruct
    public void start() {
        // 1. 获取 access_token
        wechatTokenHandler.getAccessToken();

        // 2. 调用接口

    }
}
