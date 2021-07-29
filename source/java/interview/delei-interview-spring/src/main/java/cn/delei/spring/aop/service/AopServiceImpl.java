package cn.delei.spring.aop.service;

import org.springframework.stereotype.Service;

/**
 * AopServiceImpl Bean
 *
 * @author deleiguo
 */
@Service
public class AopServiceImpl implements AopService {
    @Override
    public void run() {
        System.out.println(this.getClass().getName() + ": run()");
    }
}
