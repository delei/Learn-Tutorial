package cn.delei.spring.ioc.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IocCircularBeanB {

    @Autowired
    private IocCircularBeanA iocCircularBeanA;

    public void print() {
        System.out.println("IocCircularBeanB!");
    }
}
