package cn.delei.spring.boot.mybatis.service;

import cn.delei.spring.starter.DeleiService;
import org.springframework.stereotype.Service;

/**
 * DeleiService实现类
 *
 * @author deleiguo
 */

@Service
public class DeleiMybatisServiceImpl implements DeleiService {
    @Override
    public void loadDelei() {
        System.out.println("DeleiMybatisServiceImpl loadDelei()");
    }
}
