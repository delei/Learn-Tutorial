package com.delei.spring.boot.transaction;

import cn.delei.spring.boot.BootApplication;
import cn.delei.spring.boot.transaction.application.UserAppService;
import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.util.PrintUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BootApplication.class})
public class TxPropagationTest {

    @Autowired
    private UserAppService userAppService;

    @Test
    void addDefault() {
        PrintUtil.printDivider("TxPropagationTest -> addDefault");
        UserEntity entity = new UserEntity();
        entity.setName("Propagation-100");
        userAppService.addTxDefault(entity);
    }
}
