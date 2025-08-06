package com.example.zoo;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZooManagementApplication {
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ZooManagementApplication.class, args);
    }
}
