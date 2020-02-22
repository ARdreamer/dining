package com.order.dining.service.impl;

import com.alibaba.fastjson.JSON;
import com.order.dining.dao.domain.SellerInfo;
import com.order.dining.service.SellerInfoService;
import com.order.dining.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @Author: baojx
 * @Date: 2020/2/22 11:52
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoServiceImplTest {

    @Resource
    private SellerInfoService sellerInfoService;

    @Test
    public void selectByOpenId() {
        SellerInfo sellerInfo = sellerInfoService.selectByOpenId("1234");
        System.out.println(JSON.toJSONString(sellerInfo, true));
    }

    @Test
    public void insert() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPwd("admin");
        sellerInfo.setOpenid("1234");

        Integer insert = sellerInfoService.insert(sellerInfo);
        Assert.assertNotNull(insert);
    }
}
