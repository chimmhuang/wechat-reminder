package com.github.chimmhuang.wechat.push.cache;

import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.chimmhuang.wechat.push.dto.AccessTokenRespDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Chimm Huang
 * @date 2022/8/31
 */
@Component
public class WechatCacheClient {
    private static final Logger log = LoggerFactory.getLogger(WechatCacheClient.class);

    public WechatCacheClient(Cache<String, String> wechatTokenCache) {
        this.wechatTokenCache = wechatTokenCache;
    }

    private final Cache<String, String> wechatTokenCache;

    /**
     * 微信 access_token 缓存 key
     */
    public static final String ACCESS_TOKEN_KEY = "WECHAT_ACCESS_TOKEN_KEY";

    /**
     * 设置 access_token 缓存
     */
    public void setAccessToken(AccessTokenRespDTO accessTokenRespDTO) {
        wechatTokenCache.put(ACCESS_TOKEN_KEY, JSONUtil.toJsonStr(accessTokenRespDTO));
        log.info("已将 access_token 放入缓存");//BoundedLocalCache$BoundedLocalLoadingCache@2921a36a 7210
    }


    /**
     * 获取 access_token
     */
    public AccessTokenRespDTO getAccessToken() {
        String value = wechatTokenCache.getIfPresent(ACCESS_TOKEN_KEY);
        if (value == null || "".equals(value)) {
            log.debug("未获取到 access_token");
            return null;
        }

        return JSONUtil.toBean(value, AccessTokenRespDTO.class);
    }
}
