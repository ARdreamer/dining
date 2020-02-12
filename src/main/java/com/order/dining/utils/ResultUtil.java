package com.order.dining.utils;

import com.order.dining.common.Constants;
import com.order.dining.common.Result;

/**
 * @Author: baojx
 * @Date: 2020/2/12 12:04
 */
public class ResultUtil {

    public static Result success(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(Constants.ReturnCode.SUCCESS_CODE);
        result.setMsg(Constants.ReturnMsg.SUCCESS_MSG);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
