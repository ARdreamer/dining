package com.order.dining.controller.seller;

import com.alibaba.fastjson.JSON;
import com.order.dining.common.Constants;
import com.order.dining.dao.domain.SellerInfo;
import com.order.dining.service.SellerInfoService;
import com.order.dining.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: baojx
 * @Date: 2020/2/24 13:25
 * @Desc: 卖家信息
 */
@Controller
@RequestMapping("")
@Slf4j
public class SellerInfoController {

    @Resource
    private SellerInfoService sellerInfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index() {
        return new ModelAndView("common/index");
    }

    @GetMapping(value = {"/user/registerPage"})
    public ModelAndView registerPage() {
        return new ModelAndView("common/register");
    }

    @PostMapping(value = {"/user/login"})
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("pwd") String pwd,
                              HttpServletResponse response, HttpSession session, Map<String, Object> map) {
        SellerInfo user = sellerInfoService.login(username, pwd);
        if (user != null) {
            CookieUtil.set(response, Constants.Cookie.TOKEN, session.getId(), Constants.Cookie.EXPIRE);
            stringRedisTemplate.opsForValue().set(Constants.Redis.PREFIX + session.getId(), JSON.toJSONString(user), Constants.Redis.EXPIRE, TimeUnit.SECONDS);
            map.put("msg", "登陆成功!");
            map.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/success");
        }
        map.put("msg", "登录失败，请检查您的用户名及密码是否正确");
        map.put("url", "/sell/index");
        return new ModelAndView("common/error", map);
    }

    @PostMapping(value = {"/user/register"})
    public ModelAndView register(@Valid SellerInfo sellerInfo, BindingResult bindingResult, Map<String, Object> map) {
        log.error(JSON.toJSONString(sellerInfo));
        if (bindingResult.hasErrors()) {
            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url", "/sell/user/registerPage");
            return new ModelAndView("common/error", map);
        }
        sellerInfoService.insert(sellerInfo);
        map.put("url", "/sell/index");
        return new ModelAndView("common/success", map);
    }

    @GetMapping(value = {"/user/logout"})
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, Map<String, Object> map) {
        CookieUtil.removeAll(request, response, Constants.Cookie.TOKEN);
//        stringRedisTemplate.delete(Constants.Redis.PREFIX + session.getId());
        map.put("msg", "登出成功!");
        map.put("url", "/sell/index");
        return new ModelAndView("common/success", map);
    }
}
