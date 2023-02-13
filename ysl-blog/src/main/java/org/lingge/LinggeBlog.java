package org.lingge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@MapperScan("org.lingge.mapper")
@SpringBootApplication
@ConfigurationPropertiesScan
public class LinggeBlog {
    public static void main(String[] args) {
        SpringApplication.run(LinggeBlog.class,args);
    }
}
