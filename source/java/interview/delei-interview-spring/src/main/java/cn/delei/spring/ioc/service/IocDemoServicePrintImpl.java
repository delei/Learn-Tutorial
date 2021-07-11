package cn.delei.spring.ioc.service;

import org.springframework.stereotype.Service;

/**
 * IOCDemoService 接口多个实现
 * @author deleiguo
 */
@Service
public class IocDemoServicePrintImpl implements IocDemoService {
    @Override
    public void print() {
        System.out.println("02:PrintImpl print!");
    }
}
