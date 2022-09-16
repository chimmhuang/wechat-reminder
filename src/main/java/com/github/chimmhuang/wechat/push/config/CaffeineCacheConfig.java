package com.github.chimmhuang.wechat.push.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.chimmhuang.wechat.push.handler.WechatTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * @author Chimm Huang
 * @date 2022/8/31
 */
@Configuration
public class CaffeineCacheConfig {
    private static final Logger log = LoggerFactory.getLogger(CaffeineCacheConfig.class);

    /**
     * 配置微信 token 缓存本地缓存
     */
    @Bean("wechatTokenCache")
    public Cache<String, String> wechatTokenCache(@Lazy WechatTokenHandler wechatTokenHandler) {
        return Caffeine.newBuilder()
                // 数量上限
                .maximumSize(1024)
                // 微信 token 有限期为 7200 秒
                .expireAfterWrite(7200, TimeUnit.SECONDS)
                // 提前 1分钟 刷新 token
                .refreshAfterWrite(119, TimeUnit.MINUTES)
                // 记录下缓存的一些统计数据，例如命中率等
                .recordStats()
                // 剔除监听
                .removalListener((key, value, cause) -> log.info("key:{},value:{},删除原因:{}", key, value, cause.name()))
                // 刷新策略
                .build(k -> {
                    log.info("开始更新缓存值,key:{}", k);
                    return wechatTokenHandler.getAccessTokenFromWechat();
                });
    }
}
