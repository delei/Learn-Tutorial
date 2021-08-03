package cn.delei.spring.aop;

import cn.delei.spring.aop.service.AopService;
import cn.delei.spring.aop.service.AopServiceImpl;
import cn.delei.util.PrintUtil;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

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
        aopUtilsDemo();
        close();
    }

    /**
     * EnableAspectJAutoProxy 注解相关
     */
    static void aopAspectDemo() {
        PrintUtil.printDivider("Aop @Aspect注解");
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

    static void aopUtilsDemo() {
        PrintUtil.printDivider("AopUtils工具类");
        AopService aopService = getProxy(new AopServiceImpl());

        // AopUtils.isAopProxy:是否是代理对象
        System.out.println(AopUtils.isAopProxy(aopService));
        System.out.println(AopUtils.isJdkDynamicProxy(aopService));
        System.out.println(AopUtils.isCglibProxy(aopService));

        // 拿到目标对象
        System.out.println(AopUtils.getTargetClass(aopService));

        // selectInvocableMethod:方法@since 4.3  底层依赖于方法MethodIntrospector.selectInvocableMethod
        // 只是在他技术上做了一个判断： 必须是被代理的方法才行（targetType是SpringProxy的子类,且是private这种方法，且不是static的就不行）
        Method method = ClassUtils.getMethod(AopServiceImpl.class, "aopRun");
        System.out.println(AopUtils.selectInvocableMethod(method, AopServiceImpl.class));

        // 是否是equals方法
        // isToStringMethod、isHashCodeMethod、isFinalizeMethod  都是类似的
        System.out.println(AopUtils.isEqualsMethod(method));

        // 它是对ClassUtils.getMostSpecificMethod,增加了对代理对象的特殊处理。。。
        System.out.println(AopUtils.getMostSpecificMethod(method, AopService.class));

    }

    static AopService getProxy(Object targetObject) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(targetObject);
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            System.out.println("==> MethodBeforeAdvice");
        });

        AopService aopService = (AopService) proxyFactory.getProxy();
        aopService.aopRun();
        System.out.println(aopService.getClass().getName());
        return aopService;
    }

    static void close() {
        context.close();
    }
}
