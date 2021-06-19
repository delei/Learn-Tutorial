package cn.delei.java.lang;

import cn.delei.util.PrintUtil;
import cn.delei.java.lang.proxy.*;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * 动态代理相关 Demo
 *
 * @author deleiguo
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        JDKProxy();
        CGLIBProxy();
    }

    /**
     * JDK 动态代理 Demo
     */
    static void JDKProxy() {
        PrintUtil.printDivider("JDK 动态代理");
        // 1. 创建被代理的对象
        SubjectImpl subject = new SubjectImpl();
        // 2. 获取对应的 ClassLoader
        ClassLoader classLoader = subject.getClass().getClassLoader();
        // 3. 获取所有接口的Class
        Class[] interfaces = subject.getClass().getInterfaces();
        // 4. 创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        SubjectProxy subjectProxy = new SubjectProxy(subject);
        SubjectInterface subjectInterface = (SubjectInterface) Proxy.newProxyInstance(classLoader, interfaces, subjectProxy);
        // 5. 调用代理的方法
        System.out.println(subjectInterface.save("11111"));
        System.out.println(subjectInterface.update("22222"));
    }

    /**
     * CGLIB 动态代理 Demo
     */
    static void CGLIBProxy() {
        PrintUtil.printDivider("CGLIB 动态代理接口类");
        Enhancer enhancer = new Enhancer();
        // 真正实现业务逻辑的目标类
        enhancer.setSuperclass(SubjectImpl.class);
        enhancer.setCallback(new SujectCGLIBProxy());

        SubjectInterface subjectInterface = (SubjectInterface) enhancer.create();
        subjectInterface.save("33333");
        subjectInterface.update("44444");
        PrintUtil.printDivider("CGLIB 动态代理普通类");
        enhancer = new Enhancer();
        // 真正实现业务逻辑的目标类
        enhancer.setSuperclass(SubjectGeneral.class);
        enhancer.setCallback(new SujectCGLIBProxy());

        SubjectGeneral subjectGeneral = (SubjectGeneral) enhancer.create();
        subjectGeneral.save("33333");
        subjectGeneral.update("44444");

    }
}
