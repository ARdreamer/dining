package com.order.dining.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author: baojx
 * @Date: 2020/2/16 14:17
 * @Desc: 公众号配置
 */
@Component
public class WeChatMpConfig {

    @Resource
    private WeChatConfig weChatConfig;

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);

        return wxMpService;
    }

    @Bean
    WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(weChatConfig.getMpAppId());
        wxMpInMemoryConfigStorage.setSecret(weChatConfig.getMpAppSecret());

        return wxMpInMemoryConfigStorage;
    }
}
