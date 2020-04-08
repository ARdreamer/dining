package com.order.dining.exception.handler;

import com.order.dining.common.Result;
import com.order.dining.exception.DiningException;
import com.order.dining.exception.SellerAuthorizeException;
import com.order.dining.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: baojx
 * @Date: 2020/2/24 13:42
 * @Desc: 异常拦截器
 */
@ControllerAdvice
public class SellerExceptionHandler {


    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException() {
        //todo 增加跳转链接
        return new ModelAndView("redirect:"
                .concat("http://dining.natapp1.cc/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat("http://dining.natapp1.cc/")
                .concat("/sell/user/login"));

    }

    @ExceptionHandler(value = DiningException.class)
    public ModelAndView handlerDiningException(DiningException e) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", e.getMessage());
        map.put("url", "/sell/user/index");
        return new ModelAndView("common/error", map);
    }


//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView handleException(Exception e) {
//        Map<String, String> map = new HashMap<>();
//        map.put("msg", e.getMessage());
//        map.put("url", "/sell/user/index");
//        return new ModelAndView("common/error", map);
//    }

}
