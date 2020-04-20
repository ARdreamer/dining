package com.order.dining.aspect;

import com.order.dining.common.Constants;
import com.order.dining.common.enums.EResultError;
import com.order.dining.exception.DiningException;
import com.order.dining.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: baojx
 * @Date: 2020/2/24 13:28
 * @Desc: 卖家AOP
 */
@Component
@Aspect
@Slf4j
public class SellerAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.order.dining.controller.seller.Seller*.*(..))" +
            "&& !execution(public * com.order.dining.controller.seller.SellerInfoController.*(..))")
    public void userLoginVerify() {
    }

    @Pointcut("execution(public * com.order.dining.controller.seller.SellerInfoController.*(..))" +
            "&& !execution(public * com.order.dining.controller.seller.SellerInfoController.pwd*(..))" +
            "&& !execution(public * com.order.dining.controller.seller.SellerInfoController.logout(..))")
    public void index() {
    }

    @Before("userLoginVerify()")
    public void doVerify() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.get(request, Constants.Cookie.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new DiningException(EResultError.AUTHORIZE_ERROR);
        }
//        if (!StringUtils.equals(cookie.getValue(), request.getSession().getId())) {
//            log.warn("【登录校验】登录重复");
//            CookieUtil.removeAll(request, response, Constants.Cookie.TOKEN);
//            throw new DiningException(EResultError.LOGIN_COOKIE_REPEAT_ERROR);
//        }
        //去redis里查询
        String tokenValue = stringRedisTemplate.opsForValue().get(Constants.Redis.PREFIX + cookie.getValue());
        if (StringUtils.isBlank(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new DiningException(EResultError.AUTHORIZE_ERROR);
        }
    }

    @Before("index()")
    public void indexAOP() throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.get(request, Constants.Cookie.TOKEN);
        if (cookie != null) {
            //去redis里查询
            String tokenValue = stringRedisTemplate.opsForValue().get(Constants.Redis.PREFIX + cookie.getValue());
            if (StringUtils.isNotBlank(tokenValue)) {
                assert response != null;
                response.sendRedirect("/sell/seller/order/list");
            }
        }
    }
}