package com.order.dining.handler;

import com.order.dining.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: baojx
 * @Date: 2020/2/24 13:42
 * @Desc: 异常拦截器
 */
public class SellerExceptionHandler {


    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        //todo 增加跳转链接
        return new ModelAndView("redirect");
    }


}
