package cn.delei.spring.boot;

import cn.delei.spring.boot.autoconfigure.DeleiConfigureProperties;
import cn.delei.spring.starter.DeleiService;
import cn.delei.spring.starter.EnableDeleiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
@EnableDeleiConfiguration
public class BootApplication {

    /**
     * Main方式启动方法
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ConfigurableApplicationContext context = SpringApplication.run(BootApplication.class, args);
        // 自定义加载配置信息
        loadProperties(context);
    }

    /**
     * 获取自定义配置信息
     *
     * @param context
     */
    static void loadProperties(ConfigurableApplicationContext context) {
        DeleiConfigureProperties deleiConfigureProperties = (DeleiConfigureProperties) context
                .getBean("deleiConfigureProperties");
        // @ConfigurationProperties注解读取配置文件
        System.out.println("@ConfigurationProperties: " + deleiConfigureProperties.toString());
        // 自定义starter中的读取jar中的配置文件
        DeleiService deleiService = (DeleiService) context.getBean("deleiService");
        deleiService.loadDelei();
    }
}