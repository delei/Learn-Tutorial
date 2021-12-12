package cn.delei.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Service02Application {

    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        SpringApplication.run(Service02Application.class, args);
    }
}