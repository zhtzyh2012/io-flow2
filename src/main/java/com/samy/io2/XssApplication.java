package com.samy.io2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class XssApplication {

    public static void main(String[] args) {
        SpringApplication.run(XssApplication.class, args);
    }

}