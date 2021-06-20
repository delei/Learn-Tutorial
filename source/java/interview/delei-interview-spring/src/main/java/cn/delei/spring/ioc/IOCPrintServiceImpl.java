package cn.delei.spring.ioc;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * IOCDemoService Print额外实现
 *
 * @author deleiguo
 */
@Service
@Primary
public class IOCPrintServiceImpl implements IOCDemoService {
    @Override
    public void print() {
        System.out.println("PrintServiceImpl print!");
    }
}
