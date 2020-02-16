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

    private String mpAppId;
    private String mpAppSecret;

}
