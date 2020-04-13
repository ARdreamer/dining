package com.order.dining.utils;

import java.util.Random;

/**
 * @Author: baojx
 * @Date: 2020/2/14 13:40
 * @Desc: 生成唯一主键
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     *
     * @return 随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
