package com.order.dining.utils;

import com.github.pagehelper.PageInfo;
import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:23
 * @Desc: 分页工具类
 */
public class PageUtil {
    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageRequest 分页请求
     * @param pageInfo 分页信息
     * @return 分页结果
     */
    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

}
