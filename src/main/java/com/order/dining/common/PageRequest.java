package com.order.dining.common;

import lombok.Data;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:22
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

}
