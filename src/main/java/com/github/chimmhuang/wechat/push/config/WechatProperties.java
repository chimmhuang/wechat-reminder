package com.github.chimmhuang.wechat.push.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chimm Huang
 * @date 2022/9/16
 */
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    /**
     * 微信公众号appid
     */
    private String appID;

    /**
     * 微信公众号appSecret
     */
    private String appsecret;

    /**
     * 获取 access_token 地址
     */
    private String getAccessTokenUrl;

    /**
     * 模版推送地址
     */
    private String tmplmsgUrl;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getGetAccessTokenUrl() {
        return getAccessTokenUrl;
    }

    public void setGetAccessTokenUrl(String getAccessTokenUrl) {
        this.getAccessTokenUrl = getAccessTokenUrl;
    }

    public String getTmplmsgUrl() {
        return tmplmsgUrl;
    }

    public void setTmplmsgUrl(String tmplmsgUrl) {
        this.tmplmsgUrl = tmplmsgUrl;
    }
}
