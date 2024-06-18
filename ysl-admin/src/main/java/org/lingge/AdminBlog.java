package org.lingge;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@MapperScan("org.lingge.mapper")
@SpringBootApplication
@ConfigurationPropertiesScan
public class AdminBlog {
    public static void main(String[] args) {
        SpringApplication.run(AdminBlog.class,args);
        System.out.println("=====================博客后端后台启动成功！====================");
    }
}
