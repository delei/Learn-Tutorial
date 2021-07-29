package cn.delei.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP 上下文配置类
 *
 * @author deleiguo
 */
@ComponentScan(basePackages = "cn.delei.spring.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {
}
