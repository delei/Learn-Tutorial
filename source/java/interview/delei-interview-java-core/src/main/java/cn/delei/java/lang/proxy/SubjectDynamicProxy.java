package cn.delei.java.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 动态代理
 *
 * @author deleiguo
 */
public class SubjectDynamicProxy implements InvocationHandler {
    /**
     * 目标对象
     */
    private Object targetObject;

    public SubjectDynamicProxy(Object targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 功能增强01
        before(method.getName());
        // 调用 target 的 method 方法
        Object result = method.invoke(targetObject, args);
        // 功能增强02
        after(method.getName());
        return result;  // 返回方法的执行结果
    }

    void before(String methodName) {
        System.out.println("Proxy before:" + methodName);
    }

    void after(String methodName) {
        System.out.println("Proxy after:" + methodName);
    }
}
