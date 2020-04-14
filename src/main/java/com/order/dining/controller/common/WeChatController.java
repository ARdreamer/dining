package com.order.dining.controller.common;

import com.order.dining.common.enums.EResultError;
import com.order.dining.config.WeChatConfig;
import com.order.dining.exception.DiningException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: baojx
 * @Date: 2020/2/16 13:43
 * @Desc: 微信访问
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Resource
    private WxMpService wxMpService;
    @Resource
    private WeChatConfig weChatConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        log.info("【微信网页授权】returnUrl:{}", returnUrl);
        String url = weChatConfig.getUserInfoUrl();
        String result = null;
        try {
            result = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("【微信网页授权】returnUrl格式转换失败，returnUrl:{}", returnUrl);
            throw new DiningException(EResultError.WECHAT_MP_ERROR);
        }
        log.info("【微信网页授权】authorize，result={}", result);

        return "redirect:" + result;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信用户信息】获取token失败,", e);
            throw new DiningException(EResultError.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信用户信息】用户openId=【{}】", openId);
        log.info("【微信用户信息】redirect=【{}】", "redirect:" + returnUrl + "?openid=" + openId);
        return "redirect:" + returnUrl + "?openid=" + openId;

    }
}
