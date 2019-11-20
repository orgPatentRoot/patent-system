package com.suixingpay.patent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.suixingpay.patent.mapper")
public class PatentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatentApplication.class,args);
    }
}
