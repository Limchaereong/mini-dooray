package com.nhnacademy.minidooraydgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.nhnacademy.minidooraydgateway.client")
public class MiniDoorayDGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniDoorayDGatewayApplication.class, args);
    }
}
