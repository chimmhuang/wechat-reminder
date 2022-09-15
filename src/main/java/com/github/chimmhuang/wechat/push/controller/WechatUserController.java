package com.github.chimmhuang.wechat.push.controller;

import com.github.chimmhuang.wechat.push.cache.WechatCacheClient;
import com.github.chimmhuang.wechat.push.dto.AccessTokenRespDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Chimm Huang
 * @date 2022/9/13
 */
@RestController
public class WechatUserController {
    private static final Logger log = LoggerFactory.getLogger(WechatUserController.class);

    private final WechatCacheClient wechatCacheClient;

    public WechatUserController(WechatCacheClient wechatCacheClient) {
        this.wechatCacheClient = wechatCacheClient;
    }

    @Value("#{'${openid.list}'.split(',')}")
    private List<String> openList;



//    @PostConstruct
    public void test() {
        AccessTokenRespDTO accessToken = wechatCacheClient.getAccessToken(WechatCacheClient.ACCESS_TOKEN_KEY);
        System.out.println(accessToken.getAccess_token());
        System.out.println(openList.size());
    }
}
