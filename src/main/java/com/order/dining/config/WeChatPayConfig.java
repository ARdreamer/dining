package com.order.dining.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: baojx
 * @Date: 2020/2/17 12:39
 * @Desc: 微信支付配置
 */
@Component
public class WeChatPayConfig {
    @Resource
    private WeChatConfig weChatConfig;

    @Bean
    public BestPayServiceImpl bestPayService(WxPayH5Config wxPayH5Config) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(weChatConfig.getMpAppId());
        wxPayH5Config.setAppSecret(weChatConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(weChatConfig.getKeyPath());
        wxPayH5Config.setMchKey(weChatConfig.getMchKey());
        wxPayH5Config.setMchId(weChatConfig.getMchId());
        wxPayH5Config.setNotifyUrl(weChatConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
