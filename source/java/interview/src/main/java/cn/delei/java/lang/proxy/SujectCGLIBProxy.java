package cn.delei.java.lang.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 动态代理类
 *
 * @author deleiguo
 */
public class SujectCGLIBProxy implements MethodInterceptor {
    /**
     * @param o           表示要进行增强的对象
     * @param method      表示拦截的方法
     * @param objects     数组表示参数列表，基本数据类型需要传入其包装类型，如int-->Integer、long-Long、double-->Double
     * @param methodProxy 表示对方法的代理，invokeSuper方法表示对被代理对象方法的调用
     * @return 执行结果
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before(method.getName());
        // 注意这里是调用invokeSuper而不是invoke，否则死循环;
        // methodProxy.invokeSuper执行的是原始类的方法;
        // method.invoke执行的是子类的方法;
        Object result = methodProxy.invokeSuper(o, objects);
        after(method.getName());
        return result;
    }

    void before(String methodName) {
        System.out.println("Proxy before:" + methodName);
    }

    void after(String methodName) {
        System.out.println("Proxy after:" + methodName);
    }
}
