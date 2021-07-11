package cn.delei.spring.ioc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * IOCDemoService 默认实现
 * @author deleiguo
 */
@Service
@Primary
public class IocDemoServiceDefaultImpl implements IocDemoService {
    @Override
    public void print() {
        System.out.println("01:DefaultImpl print!");
    }
}
