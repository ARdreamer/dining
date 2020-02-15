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
    //todo construct 封装
}
