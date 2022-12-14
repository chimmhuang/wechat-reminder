package com.github.chimmhuang.wechat.push.controller;

import cn.hutool.json.JSONUtil;
import com.github.chimmhuang.wechat.push.cache.WechatCacheClient;
import com.github.chimmhuang.wechat.push.config.WechatProperties;
import com.github.chimmhuang.wechat.push.dto.AccessTokenRespDTO;
import com.github.chimmhuang.wechat.push.dto.SendTemplateMsgDTO;
import com.github.chimmhuang.wechat.push.handler.WechatTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author Chimm Huang
 * @date 2022/9/16
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(WechatCacheClient wechatCacheClient, WechatProperties wechatProperties, RestTemplate restTemplate, WechatTokenHandler wechatTokenHandler) {
        this.wechatCacheClient = wechatCacheClient;
        this.wechatProperties = wechatProperties;
        this.restTemplate = restTemplate;
        this.wechatTokenHandler = wechatTokenHandler;
    }

    private final WechatCacheClient wechatCacheClient;
    private final WechatProperties wechatProperties;
    private final RestTemplate restTemplate;
    private final WechatTokenHandler wechatTokenHandler;

    /**
     * 整点报时模板id
     */
    @Value("${wechat.template.timer}")
    private String timerTemplateId;

    /**
     * 微信用户 openId
     */
    @Value("#{'${openid.list}'.split(',')}")
    private List<String> openIdList;

    /**
     * 推送模板消息
     */
    @GetMapping("/template/send")
    public void pushTemplateMsg() {
        // 获取 access_token
        AccessTokenRespDTO accessToken = wechatCacheClient.getAccessToken();
        if (accessToken == null) {
            log.info("未从缓存中获取到 access_token");
            String accessTokenStr = wechatTokenHandler.getAccessToken();
            // 放入缓存
            accessToken = JSONUtil.toBean(accessTokenStr, AccessTokenRespDTO.class);
            wechatCacheClient.setAccessToken(accessToken);
        }

        // 获取模板推送地址
        String tmplmsgUrl = String.format(wechatProperties.getTmplmsgUrl(), accessToken.getAccess_token());

        // 组装内容
        Map<String, SendTemplateMsgDTO.DataValue> dataMap = new HashMap<>();
        dataMap.put("date", SendTemplateMsgDTO.DataValue.builder().value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).color("#DC3714").build());

        openIdList.parallelStream().forEach(openId -> {
            SendTemplateMsgDTO msgDTO = SendTemplateMsgDTO.builder()
                    .touser(openId)
                    .template_id(timerTemplateId)
                    .data(dataMap)
                    .build();
            log.info("开始请求微信公众号推送:openId:{}", openId);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(tmplmsgUrl, JSONUtil.toJsonStr(msgDTO), String.class);
            log.info("微信公众号推送结束:openId:{},状态码:{},body:{}", openId, responseEntity.getStatusCode().value(), responseEntity.getBody());
        });
    }
}
