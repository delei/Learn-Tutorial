package cn.delei.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RocketMQApplication {
    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(RocketMQApplication.class, args);

    }
}
