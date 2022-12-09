package com.shms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;

@MapperScan("com.shms.common.mapper")
@MapperScan("com.shms.admin.mapper")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {

    @Value("${server.servlet.context-path}")
    private static String path;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);
    }

}
