package cn.delei.spring.ioc.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IocCircularBeanA {

    @Autowired
    private IocCircularBeanB iocCircularBeanB;

    public void print() {
        System.out.println("IocCircularBeanA!");
    }
}
