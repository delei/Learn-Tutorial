package com.delei.spring.boot.transaction;

import cn.delei.spring.boot.BootApplication;
import cn.delei.spring.boot.transaction.facade.TxFacade;
import cn.delei.util.PrintUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 事务传播单元测试(Junit5)
 *
 * @author deleiguo
 */
@SpringBootTest(classes = {BootApplication.class})
public class TxPropagationTest {

    @Autowired
    private TxFacade txFacade;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach() {
        // 清空表数据
        System.out.println("==> beforeEach sql");
        String sql = "truncate table user_student";
        jdbcTemplate.execute(sql);
        sql = "truncate table user_teacher";
        jdbcTemplate.execute(sql);
    }

    @AfterEach
    public void afterEach() {
        System.out.println("==> afterEach");
    }

    @Test
    void addDefault() {
        PrintUtil.printDivider("TxPropagationTest -> addDefault");
        txFacade.addTxDefault();
    }

    @Test
    void addRequired() {
        /*
         * Propagation.REQUIRED
         * 外部方法未开启事务的情况下,内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰
         * 外部方法异常不影响内部事务
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequired");
        txFacade.addRequired();
    }

    @Test
    void addRequiredException() {
        /*
         * Propagation.REQUIRED
         * 外部方法未开启事务的情况下,内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredException");
        txFacade.addRequiredException();
    }

    @Test
    void addRequiredExceptionTx() {
        /*
         * Propagation.REQUIRED
         * 外部方法也开启事务的情况
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredException");
        txFacade.addRequiredExceptionTx();
    }

    @Test
    void addRequiredNew() {
        /*
         * Propagation.REQUIRES_NEW
         * 外部方法未开启事务的情况下,内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰
         * 外部方法异常不影响内部事务
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNew");
        txFacade.addRequiredNew();
    }

    @Test
    void addRequiredNewException() {
        /*
         * Propagation.REQUIRES_NEW
         * 外部方法未开启事务的情况下,内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰
         * 外部方法异常不影响内部事务
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNewException");
        txFacade.addRequiredNewException();
    }

    @Test
    void addRequiredNewTx001() {
        /*
         * Propagation.REQUIRES_NEW
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNewTx001");
        txFacade.addRequiredNewTx001();
    }

    @Test
    void addRequiredNewTx002() {
        /*
         * Propagation.REQUIRES_NEW
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNewTx002");
        txFacade.addRequiredNewTx002();
    }

    @Test
    void addRequiredNewTx003() {
        /*
         * Propagation.REQUIRES_NEW
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNewTx003");
        txFacade.addRequiredNewTx003();
    }

    @Test
    void addRequiredNested() {
        /*
         * Propagation.NESTED
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNested");
        txFacade.addRequiredNested();
    }

    @Test
    void addRequiredNestedException() {
        /*
         * Propagation.NESTED
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNestedException");
        txFacade.addRequiredNestedException();
    }

    @Test
    void addRequiredNestedTx001() {
        /*
         * Propagation.NESTED
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNestedTx001");
        txFacade.addRequiredNestedTx001();
    }

    @Test
    void addRequiredNestedTx002() {
        /*
         * Propagation.NESTED
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNestedTx002");
        txFacade.addRequiredNestedTx002();
    }

    @Test
    void addRequiredNestedTx003() {
        /*
         * Propagation.NESTED
         * 外部方法开启事务的情况下
         */
        PrintUtil.printDivider("TxPropagationTest -> addRequiredNestedTx003");
        txFacade.addRequiredNestedTx003();
    }
}
