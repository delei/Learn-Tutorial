package cn.delei.java.lang;

import cn.delei.java.lang.proxy.*;
import cn.delei.util.PrintUtil;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理相关 Demo
 *
 * @author deleiguo
 */
public class DynamicProxyDemo {
    public static void main(String[] args) throws Exception{
        //JDKProxy();
        //JDKDynamicProxy();
        //CGLIBProxy();
        JavassistFactoryProxy();
    }

    static void JDKProxy() {
        PrintUtil.printDivider("JDK 静态代理");
        SubjectInterface subjectInterface = new SubjectProxy(new SubjectImpl());
        subjectInterface.save("111111");
        subjectInterface.update("222222");
    }

    /**
     * JDK 动态代理 Demo
     */
    static void JDKDynamicProxy() {
        // 设置该变量可以保存动态代理类，默认名称$Proxy0.class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        PrintUtil.printDivider("JDK 动态代理");
        // 1. 创建被代理的对象
        SubjectImpl subject = new SubjectImpl();
        // 2. 获取对应的 ClassLoader
        ClassLoader classLoader = subject.getClass().getClassLoader();
        // 3. 获取所有接口的Class
        Class[] interfaces = subject.getClass().getInterfaces();
        // 4. 创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        SubjectDynamicProxy subjectProxy = new SubjectDynamicProxy(subject);
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

    /**
     * Javassist 代理工厂
     */
    static void JavassistFactoryProxy() throws Exception {
        // 创建代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();

        // 设置被代理类的类型
        proxyFactory.setSuperclass(SubjectImpl.class);
        // 创建代理类的class
        Class<?> proxyClass = proxyFactory.createClass();
        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                System.out.println("==> Proxy before: " + thisMethod.getName());
                Object invoke = proceed.invoke(self,args);
                System.out.println("==> Proxy after: " + thisMethod.getName());
                return invoke;
            }
        };
        // 创建对象
        javassist.util.proxy.Proxy proxy = (javassist.util.proxy.Proxy) proxyClass.getDeclaredConstructor()
                .newInstance((Object[]) null);
        proxy.setHandler(methodHandler);

        SubjectInterface subjectInterface = (SubjectInterface) proxy;
        subjectInterface.save("222222");
    }
}
