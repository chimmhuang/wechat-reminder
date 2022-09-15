package com.github.chimmhuang.wechat.push.handler;

import com.github.chimmhuang.wechat.push.cache.WechatCacheClient;
import com.github.chimmhuang.wechat.push.dto.AccessTokenRespDTO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Chimm Huang
 * @date 2022/9/13
 */
@Component
public class WechatTemplateMsgHandler {
    private static final Logger log = LoggerFactory.getLogger(WechatTemplateMsgHandler.class);

    @Lazy
    public WechatTemplateMsgHandler(RestTemplate restTemplate, WechatCacheClient wechatCacheClient) {
        this.restTemplate = restTemplate;
        this.wechatCacheClient = wechatCacheClient;
    }

    private final RestTemplate restTemplate;
    private final WechatCacheClient wechatCacheClient;

    /**
     * 模版推送地址
     */
    @Value("${wechat.tmplmsgUrl}")
    private String tmplmsgUrl;

    @PostConstruct
    public void test() {
        AccessTokenRespDTO accessToken = wechatCacheClient.getAccessToken(WechatCacheClient.ACCESS_TOKEN_KEY);
        if (accessToken == null) {
            log.error("未查询到 accessToken ");
            return;
        }

        // 完善接口地址
        String uri = String.format(tmplmsgUrl, accessToken.getAccess_token());
        // 调用接口
//        restTemplate.postForEntity(uri,)
    }
}
