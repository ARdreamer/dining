package com.order.dining.common.page;

import lombok.Data;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:22
 * @Desc: 分页结果封装
 */
@Data
public class PageResult {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;

}
