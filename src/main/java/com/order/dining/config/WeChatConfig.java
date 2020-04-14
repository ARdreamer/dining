package com.order.dining.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: baojx
 * @Date: 2020/2/16 14:23
 * @Desc: 微信相关配置文件
 */
@ConfigurationProperties(prefix = "wechat")
@Data
@Component
public class WeChatConfig {

    /**
     * 用户信息url
     */
    private String userInfoUrl;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * appId
     */
    private String mpAppId;

    /**
     * appSecret
     */
    private String mpAppSecret;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 证书路径
     */
    private String keyPath;

    /**
     * 通知回调地址
     */
    private String notifyUrl;

}
