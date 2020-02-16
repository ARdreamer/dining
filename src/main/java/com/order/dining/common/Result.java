package com.order.dining.common;

import lombok.Data;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:47
 * @Desc: 返回值封装
 */
@Data
public class Result<T> {
    /**
     * 错误码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    T data;

    public Result() {
        this(null);
    }

    public Result(T data) {
        code = Constants.ReturnCode.SUCCESS_CODE;
        msg = Constants.ReturnMsg.SUCCESS_MSG;
        this.data = data;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
