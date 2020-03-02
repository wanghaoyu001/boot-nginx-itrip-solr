package com.boot.bootnginxitripsolr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.controller", "com.service", "com.service.Impl"})
@MapperScan(basePackages = {"com.mapper"})
public class BootNginxItripSolrApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootNginxItripSolrApplication.class, args);
    }

}
