package com.delei.spring.boot.transaction;

import cn.delei.spring.boot.BootApplication;
import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.spring.boot.transaction.domain.user.service.TxUserDomainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BootApplication.class})
public class TxUserDomainServiceTest {

    @Autowired
    private TxUserDomainService txUserDomainService;

    @Test
    void addTest() {
        UserEntity entity = new UserEntity();
        entity.setName("deleiguo");
        txUserDomainService.add(entity);
    }

    @Test
    void deleteTest() {
        txUserDomainService.deleteByPrimaryKey(1L);
    }
}
