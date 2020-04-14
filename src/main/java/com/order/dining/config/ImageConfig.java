package com.order.dining.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Author: baojx
 * @Date: 2020/04/08 20:59
 * @Desc:
 */
@Configuration
@ConfigurationProperties(prefix = "image")
@Data
public class ImageConfig {
    
    private String realPath;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(104857600);
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxInMemorySize(40960);
        return resolver;
    }
}
