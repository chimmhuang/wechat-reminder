package com.github.chimmhuang.wechat.push.handler;

import cn.hutool.json.JSONUtil;
import com.github.chimmhuang.wechat.push.cache.WechatCacheClient;
import com.github.chimmhuang.wechat.push.dto.AccessTokenRespDTO;
import com.github.chimmhuang.wechat.push.exception.WechatTokenException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 微信 token 处理器
 *
 * @author Chimm Huang
 * @date 2022/8/31
 */
@Component
public class WechatTokenHandler {
    private static final Logger log = LoggerFactory.getLogger(WechatTokenHandler.class);

    @Lazy
    public WechatTokenHandler(RestTemplate restTemplate, WechatCacheClient wechatCacheClient) {
        this.restTemplate = restTemplate;
        this.wechatCacheClient = wechatCacheClient;
    }

    private final RestTemplate restTemplate;
    private final WechatCacheClient wechatCacheClient;

    @Value("${wechat.getAccessTokenUrl}")
    private String getAccessTokenUrl;

    @Value("${wechat.appID}")
    private String appID;

    @Value("${wechat.appsecret}")
    private String appSecret;

    /**
     * 获取 access_token
     * 微信接口文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">获取Access token</a>
     */
    @PostConstruct
    public AccessTokenRespDTO getAccessToken() {
        // 从缓存中获取
        AccessTokenRespDTO accessToken = wechatCacheClient.getAccessToken(WechatCacheClient.ACCESS_TOKEN_KEY);
        if (accessToken != null) {
            log.info("从缓存获取到 access_token:{}", JSONUtil.toJsonStr(accessToken));
            return accessToken;
        }

        String body = this.getAccessTokenFromWechat();
        AccessTokenRespDTO accessTokenRespDTO = JSONUtil.toBean(body, AccessTokenRespDTO.class);
        log.info("从微信获取到 access_token:{}", JSONUtil.toJsonStr(accessTokenRespDTO));

        // 将其写入缓存
        wechatCacheClient.setAccessToken(WechatCacheClient.ACCESS_TOKEN_KEY, accessTokenRespDTO);
        return accessTokenRespDTO;
    }

    public String getAccessTokenFromWechat() {
        // 完善接口地址
        String uri = String.format(getAccessTokenUrl, appID, appSecret);
        // 调用接口
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        log.info("调用获取 access_token 接口返回状态码：{}，返回值：{}", responseEntity.getStatusCode(), responseEntity.getBody());
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("调用微信接口：获取 access_token 接口，返回状态码异常,status:{}", responseEntity.getStatusCode());
            throw new WechatTokenException("获取微信 access_token 失败");
        }

        /*
            正确的返回结果
            {
                "access_token": "60_e54mTyN8LCAedkj9XSx2pptvrRfn6HZMUGyM56hMVrYlnySH6-RUOcDXm5fNEbCBK80xo7z67bV-iELGqzLxFCqdZ3aaX4WG5eqLCbI_LrI7C7IX95qF75NRZCAnOJOFKjJkWcWf8MtthRt_LJQaABAFDK",
                "expires_in": 7200
            }
         */
        String body = responseEntity.getBody();
        log.info("获取到 access_token:{}", body);
        return body;
    }
}
