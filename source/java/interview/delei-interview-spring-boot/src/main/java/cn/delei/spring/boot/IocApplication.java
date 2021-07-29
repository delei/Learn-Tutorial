package cn.delei.spring.boot;

import cn.delei.spring.boot.ioc.service.IocDemoBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * IOC Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
public class IocApplication {

    public static void main(String[] args) {
        // ConfigurableApplicationContext 相关子类
        AnnotationConfigServletWebServerApplicationContext context =
                (AnnotationConfigServletWebServerApplicationContext) SpringApplication.run(IocApplication.class, args);
        // 自定义加载配置信息
        iocDemo(context);
    }

    static void iocDemo(ApplicationContext context) {
        IocDemoBean iocDemoBean = context.getBean("iocDemoBean", IocDemoBean.class);
        iocDemoBean.run();
    }
}