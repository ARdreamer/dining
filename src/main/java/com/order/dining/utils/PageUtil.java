package com.order.dining.utils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.order.dining.common.PageRequest;
import com.order.dining.common.PageResult;
import com.order.dining.converter.PayOrder2OrderDtoConverter;
import com.order.dining.dao.domain.PayOrder;
import com.order.dining.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/2/12 13:23
 * @Desc: 分页工具类
 */
@Slf4j
public class PageUtil {
    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageInfo 分页信息
     * @return 分页结果
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        log.info("【分页结果展示】PageResult={}", JSON.toJSONString(pageResult, true));
        return pageResult;
    }

}
