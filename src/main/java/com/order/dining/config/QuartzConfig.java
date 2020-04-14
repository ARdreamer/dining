package com.order.dining.config;

import com.order.dining.quartz.CloseOrderJob;
import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: baojx
 * @Date: 2020/04/14 18:33
 * @Desc: 定时任务配置类
 */

@Configuration
@ConfigurationProperties(prefix = "time")
@Data
public class QuartzConfig {
    private String closeCron;

    @Bean
    public JobDetail closeOrderJobDetail() {
        return JobBuilder.newJob(CloseOrderJob.class)
                .withIdentity("CloseOrderJob")
                .usingJobData("msg", "Hello Quartz")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger closeOrderJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(getCloseCron());
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(closeOrderJobDetail())
                .withIdentity("quartzTaskService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
