package cn.delei.spring.ioc.service;

import cn.delei.spring.ioc.service.IocDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * IOC bean注入
 *
 * @author deleiguo
 */
@Component
public class IocDemoBean {

    @Qualifier("iocDemoServicePrintImpl")
    @Autowired
    private IocDemoService iocDemoService;

    @Resource
    private IocDemoService iocDemoServicePrintImpl;
    @Resource
    private IocDemoService iocDemoServiceDefaultImpl;

    public void run() {
        // 同一个service多个实现类

        // @Autowired 配合@Qualifier或@Primary
        iocDemoService.print();

        // @Resource
        iocDemoServiceDefaultImpl.print();
        iocDemoServicePrintImpl.print();
    }
}
