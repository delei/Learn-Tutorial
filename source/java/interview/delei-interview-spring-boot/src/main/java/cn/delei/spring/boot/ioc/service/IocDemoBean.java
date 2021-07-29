package cn.delei.spring.boot.ioc.service;

import org.springframework.stereotype.Component;

/**
 * IOC bean注入
 *
 * @author deleiguo
 */
@Component
public class IocDemoBean {

    public void run() {
        System.out.println(this.getClass().getName()+": run()");
    }
}
