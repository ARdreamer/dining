package com.order.dining.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: baojx
 * @Date: 2020/2/24 13:28
 * @Desc: 卖家AOP
 */
@Component
@Aspect
@Slf4j
public class SellerAspect {

    @Pointcut("execution(public * com.order.dining.controller.seller.Seller*.*(..))" +
            "&& !execution(public * com.order.dining.controller.seller.SellerInfoController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //todo 增加CookieUtil 查询cookie
        Cookie[] cookies = request.getCookies();
    }
}
