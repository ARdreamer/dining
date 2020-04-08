package com.order.dining.common;

/**
 * @Author: baojx
 * @Date: 2020/2/6 17:36
 * @Desc: 常用常量类
 */
public class Constants {
    public static class ReturnCode {
        public static final int SUCCESS_CODE = 0;

    }

    public static class ReturnMsg {
        public static final String SUCCESS_MSG = "成功";
    }

    public static class Pay {
        //商家名称，可以视具体商家名称更改
        public static String ORDER_NAME = "微信点餐";

        public static double DOUBLE_VALUE = 0.01;
    }

    public static class Cookie {
        public static String TOKEN = "SELL_DINING_LOGIN_TOKEN";

        public static Integer EXPIRE = 60 * 60 * 2;
    }

    public static class Redis {

        public static String PREFIX = "SELL_DINING_TOKEN:";

        public static Integer EXPIRE = 60 * 60 * 2; //2小时
    }

    public static class Image{
        public static String PATH = "/sell/image/upload/";
    }
}
