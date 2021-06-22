package cn.delei;

import cn.delei.spring.starter.EnableDeleiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application 运行入口
 *
 * @author deleiguo
 */
@SpringBootApplication
@EnableDeleiConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}