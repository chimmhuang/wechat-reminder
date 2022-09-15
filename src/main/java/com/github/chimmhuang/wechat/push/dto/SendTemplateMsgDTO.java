package com.github.chimmhuang.wechat.push.dto;

import java.util.Map;

/**
 * @author Chimm Huang
 * @date 2022/8/23
 */
public class SendTemplateMsgDTO {

    /**
     * 接收者openid
     * 【必填】
     */
    private String touser;

    /**
     * 模板ID
     * 【必填】
     */
    private String template_id;

    /**
     * 模板跳转链接（海外帐号没有跳转能力）
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private Miniprogram miniprogram;

    /**
     * 模板数据
     * key-变量
     * value-变量值
     * 【必填】
     */
    private Map<String,DataValue> data;

    /**
     * 防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
     */
    private String client_msg_id;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Miniprogram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Miniprogram miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, DataValue> getData() {
        return data;
    }

    public void setData(Map<String, DataValue> data) {
        this.data = data;
    }

    public String getClient_msg_id() {
        return client_msg_id;
    }

    public void setClient_msg_id(String client_msg_id) {
        this.client_msg_id = client_msg_id;
    }

    public static class Miniprogram {
        /**
         * 所需跳转到的小程序appid（该小程序 appid 必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
         * 【必填】
         */
        private String appid;

        /**
         * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
         */
        private String pagepath;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPagepath() {
            return pagepath;
        }

        public void setPagepath(String pagepath) {
            this.pagepath = pagepath;
        }
    }

    public static class DataValue {
        /**
         * 值
         * 【必填】
         */
        private String value;

        /**
         * 模板内容字体颜色，不填默认为黑色
         * eg. #173177
         */
        private String color;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

}
