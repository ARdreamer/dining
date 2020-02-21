package com.order.dining.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:22
 * @Desc: 分页请求封装
 */
@Data
@AllArgsConstructor
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
