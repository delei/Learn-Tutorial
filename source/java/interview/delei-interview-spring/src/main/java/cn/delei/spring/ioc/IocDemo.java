package cn.delei.spring.ioc;

import cn.delei.spring.ioc.component.IocCircularBeanA;
import cn.delei.spring.ioc.component.IocComponentConfig;
import cn.delei.spring.ioc.service.IocServiceConfig;
import cn.delei.util.PrintUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * IOC 相关Demo(非Web环境)
 *
 * @author deleiguo
 */
public class IocDemo {

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext();
        // 普通ioc bean
        iocComponentDemo();
        // service方式
//        iocServiceDemo();
    }

    static void iocComponentDemo() {
        // 注册配置类
        context.register(IocComponentConfig.class);
        context.refresh();
        circularBean();
    }

    static void iocServiceDemo() {
        // 注册配置类
        context.register(IocServiceConfig.class);
        // 手动注入BeanDefinition
        beanDefinition();
    }

    static void beanDefinition() {
        // 手动注入Bean
        String beanName = "iocGenericService";
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClassName("cn.delei.spring.ioc.service.IocGenericService");
        genericBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        genericBeanDefinition.setDescription("手动注入IocGenericService");
        genericBeanDefinition.setAbstract(false);
        // 将beanDefinition注册到spring容器中
        context.registerBeanDefinition(beanName, genericBeanDefinition);
        // 加载或刷新当前的上下文
        context.refresh();

        // 获取bean信息
        BeanDefinition beanDefinition = context.getBeanDefinition(beanName);
        PrintUtil.printDivider("手动注入iocGenericService");
        System.out.printf("%-15s\t %s\n", "父类", beanDefinition.getParentName());
        System.out.printf("%-15s\t %s\n", "描述", beanDefinition.getDescription());
        System.out.printf("%-15s\t %s\n", "在spring的名称", beanDefinition.getBeanClassName());
        System.out.printf("%-15s\t %s\n", "实例范围", beanDefinition.getScope());
        System.out.printf("%-15s\t %s\n", "是否是懒加载", beanDefinition.isLazyInit());
        System.out.printf("%-15s\t %s\n", "是否是抽象类", beanDefinition.isAbstract());
    }

    static void circularBean() {
        String beanName = "iocCircularBeanA";
        IocCircularBeanA iocCircularBeanA = context.getBean(beanName, IocCircularBeanA.class);
        iocCircularBeanA.print();
    }
}
