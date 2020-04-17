package com.order.dining.controller.seller;

import com.alibaba.fastjson.JSON;
import com.order.dining.common.Constants;
import com.order.dining.dao.domain.SellerInfo;
import com.order.dining.service.SellerInfoService;
import com.order.dining.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
//        log.error(JSON.toJSONString(sellerInfo));
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

    @GetMapping(value = {"/user/pwdResetPage"})
    public ModelAndView pwdResetPage() {
        return new ModelAndView("common/pwdReset");
    }

    @PostMapping(value = {"/user/pwdReset"})
    public ModelAndView pwdReset(@RequestParam(value = "oriPwd", defaultValue = "") String oriPwd,
                                 @RequestParam(value = "nowPwd", defaultValue = "") String nowPwd,
                                 Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        //1. 判断密码是否相同
        if (StringUtils.equals(oriPwd, nowPwd)) {
            map.put("msg", "修改密码失败，现密码与原密码相同");
            map.put("url", "/sell/user/pwdResetPage");
            return new ModelAndView("common/error", map);
        }
        //2. 获取cookie
        Cookie cookie = CookieUtil.get(request, Constants.Cookie.TOKEN);
        if (cookie == null) {
            map.put("msg", "登录异常，请重新登录");
            map.put("url", "/sell/user/logout");
            return new ModelAndView("common/error", map);
        }
        //3. Redis中获取用户信息
        SellerInfo sellerInfo = JSON.parseObject(stringRedisTemplate.opsForValue().get(Constants.Redis.PREFIX + cookie.getValue()), SellerInfo.class);
        assert sellerInfo != null;
        if (StringUtils.equals(sellerInfo.getPwd(), oriPwd)) {
            sellerInfoService.pwdReset(sellerInfo,nowPwd);
        } else {
            map.put("msg", "原密码错误，请重试");
            map.put("url", "/sell/user/pwdResetPage");
            return new ModelAndView("common/error", map);
        }
        //4. 修改成功，移除cookie
        CookieUtil.removeAll(request, response, Constants.Cookie.TOKEN);
        map.put("msg", "修改密码成功,请重新登录");
        map.put("url", "/sell/index");
        return new ModelAndView("common/success");

    }
}
