package cn.delei.spring.aop;

import cn.delei.spring.aop.service.AopService;
import cn.delei.spring.aop.service.AopServiceImpl;
import cn.delei.util.PrintUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AOP 相关Demo
 *
 * @author deleiguo
 */
public class AopDemo {

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(AopConfig.class);
        aopAspectDemo();
        close();
    }

    /**
     * EnableAspectJAutoProxy 注解相关
     */
    static void aopAspectDemo() {
        /*
         * 此处请分别修改 AopConfig 类中注解 @EnableAspectJAutoProxy 中的 proxyTargetClass 属性值
         *
         * proxyTargetClass 默认值为false，即默认使用 JDK 动态代理
         * proxyTargetClass 如果设置为 true ，则使用 CGLIB 代理
         */
        AopService aopService01 = (AopService) context.getBean("aopServiceImpl");
        aopService01.run();
        System.out.println(aopService01.getClass().getName());
        PrintUtil.printDivider();

        // proxyTargetClass 为 false 时，以下写法会抛出 NoSuchBeanDefinitionException
        AopService aopService02 = context.getBean(AopServiceImpl.class);
        aopService02.run();
        System.out.println(aopService02.getClass().getName());
    }

    static void close() {
        context.close();
    }

}
