package cn.delei.spring.aop.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面类
 *
 * @author deleiguo
 */
@Component
@Aspect
public class AopServiceAspect {
    /**
     * AOP Pointcut
     */
    private final static String POINTCUT_AOP = "execution(* cn.delei.spring.aop.service..*.run(..))";

    /**
     * 这句话是方法切入点
     * - execution (* cn.delei.spring.aop.service..*.*(..))
     * - execution ： 表示执行
     * - 第一个*号 : 表示返回值类型， *可以是任意类型
     * - cn.delei.spring.aop.service : 代表扫描的包
     * - .. : 代表其底下的子包也进行拦截
     * - 第二个*号 : 代表对哪个类进行拦截，*代表所有类
     * - 第三个*号 : 代表方法  *代表任意方法
     * - (..) : 代表方法的参数有无都可以
     */
    @Pointcut(POINTCUT_AOP)
    public void aopAspect() {
    }

    @Before("aopAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("===> doBefore");
    }

    @After("aopAspect()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("===> doAfter");
    }

    @AfterReturning("aopAspect()")
    public void doAfterReturning() {
        System.out.println("===> doAfterReturning");
    }

    @AfterThrowing("aopAspect()")
    public void doAfterThrowing() {
        System.out.println("===> doAfterThrowing");
    }

    @Around("aopAspect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("===> doAround");
        return proceedingJoinPoint.proceed();
    }
}
