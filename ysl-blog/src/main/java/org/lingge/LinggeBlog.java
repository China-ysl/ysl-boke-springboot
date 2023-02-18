package org.lingge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("org.lingge.mapper")
@SpringBootApplication
@ConfigurationPropertiesScan
//@EnableScheduling定时任务注解
@EnableScheduling
public class LinggeBlog {
    public static void main(String[] args) {
        SpringApplication.run(LinggeBlog.class,args);
    }
}
