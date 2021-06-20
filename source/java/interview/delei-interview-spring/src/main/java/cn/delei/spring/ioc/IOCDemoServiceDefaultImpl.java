package cn.delei.spring.ioc;

import org.springframework.stereotype.Service;

/**
 * IOCDemoService 默认实现
 * @author deleiguo
 */
@Service
public class IOCDemoServiceDefaultImpl implements IOCDemoService {
    @Override
    public void print() {
        System.out.println("DefaultImpl print!");
    }
}
