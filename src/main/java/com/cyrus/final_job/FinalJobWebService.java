package com.cyrus.final_job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cyrus.final_job.dao")
public class FinalJobWebService {
    public static void main(String[] args) {
        SpringApplication.run(FinalJobWebService.class, args);
    }
}
