package com.order.dining.controller.common;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: baojx
 * @Date: 2020/04/09 22:49
 * @Desc:
 */
@RequestMapping("")
@Controller
public class CustomErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    @ResponseBody
    public ModelAndView getErrorPath(HttpServletRequest request) {
        int index = request.getRequestURI().indexOf("sell.com");
        Map<String, String> map = new HashMap<>();
        if (index >= 0) {
            map.put("msg", "不可预知的错误，即将为您返回主页！");
            map.put("url", "/sell/index");
        } else {
            map.put("msg", "不可预知的错误，即将为您返回主页！");
            //todo 删除http://sell.com/#
            map.put("url", "http://sell.com/#");
        }
        return new ModelAndView("common/error", map);

    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
