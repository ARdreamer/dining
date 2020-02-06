package com.order.dining;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: baojx
 * @Date: 2020/1/29 19:41
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.order.dining.dao.mappers")
public class DiningApplication {

    public static void main(String[] args) {
        log.error("project init");
        SpringApplication.run(DiningApplication.class, args);
    }

}
