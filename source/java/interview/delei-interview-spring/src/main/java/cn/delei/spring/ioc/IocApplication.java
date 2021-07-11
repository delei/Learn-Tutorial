package cn.delei.spring.ioc;

import cn.delei.spring.ioc.component.IocDemoBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * IOC Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
public class IocApplication {

    public static void main(String[] args) {
        // Spring Boot 启动入口
        // ConfigurableApplicationContext
        ApplicationContext context = SpringApplication.run(IocApplication.class, args);
        // 自定义加载配置信息
        iocDemo(context);
    }

    static void iocDemo(ApplicationContext context) {
        IocDemoBean iocDemoBean = context.getBean("iocDemoBean", IocDemoBean.class);
        iocDemoBean.run();
    }
}