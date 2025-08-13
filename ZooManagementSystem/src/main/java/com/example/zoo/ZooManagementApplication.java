package com.example.zoo;

import com.example.zoo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ZooManagementApplication implements CommandLineRunner {

    @Autowired
    private NhanVienService nhanVienService;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SpringApplication.run(ZooManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}