package cn.delei.spring.ioc.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * bean生命周期
 *
 * @author deleiguo
 */
@Component
@Scope("singleton")
public class IocGenericBeanLifeCircle implements BeanPostProcessor, InitializingBean, DisposableBean,
        BeanFactoryAware, BeanNameAware {
    /**
     * 自定义属性
     */
    private String name;
    private int age;

    private String beanName;
    private BeanFactory beanFactory;

    public IocGenericBeanLifeCircle() {
        System.out.println("[构造器]==>调用IocGenericBeanLifeCircle的构造器实例化");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("[BeanFactoryAware]==>调用BeanNameAware.setBeanName()");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("[BeanNameAware接口]==>调用BeanNameAware.setBeanName()");
        this.beanName = name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[DiposibleBean接口]==>调用DiposibleBean.destory()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[InitializingBean接口]==>调用InitializingBean.afterPropertiesSet()");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor接口]==>方法postProcessBeforeInitialization对属性进行更改！");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor接口]==>方法postProcessAfterInitialization对属性进行更改！");
        return bean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("[注入属性]==>注入属性name");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("[注入属性]==>注入属性age");
        this.age = age;
    }

    @Override
    public String toString() {
        return "IocGenericBeanLifeCircle{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
