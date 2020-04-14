package com.order.dining.quartz;

import com.alibaba.fastjson.JSON;
import com.order.dining.beans.dto.OrderDTO;
import com.order.dining.common.enums.EOrderStatus;
import com.order.dining.converter.PayOrder2OrderDtoConverter;
import com.order.dining.dao.domain.PayOrder;
import com.order.dining.dao.mappers.PayOrderMapper;
import com.order.dining.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: baojx
 * @Date: 2020/04/14 18:32
 * @Desc: 关单任务
 */

@Slf4j
@Component
public class CloseOrderJob extends QuartzJobBean {
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private PayOrderMapper payOrderMapper;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("=============定时任务开始============");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -2);
        String endTime = simpleDateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String beginTime = simpleDateFormat.format(calendar.getTime());

        List<PayOrder> payOrderList = payOrderMapper.selectClose(beginTime, endTime, EOrderStatus.NEW.getCode().byteValue());
        List<OrderDTO> orderDTOList = PayOrder2OrderDtoConverter.convert(payOrderList);

        for (OrderDTO order : orderDTOList) {
            try {
                log.error("【关单提醒】关闭订单:{}", JSON.toJSONString(order, true));
                payOrderService.close(order);
            } catch (Exception e) {
                log.error("【关单提醒】关闭订单失败:{}", JSON.toJSONString(order, true));
            }
        }
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -2);
        System.out.println(calendar.getTime());
    }
}
