package cn.delei.spring.boot.transaction.facade;

import cn.delei.spring.boot.transaction.application.TeacherAppService;
import cn.delei.spring.boot.transaction.application.UserAppService;
import cn.delei.spring.boot.transaction.domain.teacher.entity.TeacherEntity;
import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TxFacade {
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private TeacherAppService teacherAppService;

    private final static String UT_PREFX_USER = "UT_Propagation_USER_";
    private final static String UT_PREFX_TEACHER = "UT_Propagation_TEACHER_";
    private final static AtomicInteger atomicInteger = new AtomicInteger(100);

    public void addTxDefault() {
        String id = "100";
        userAppService.addTxDefault(getUserEntity(id));
        teacherAppService.addTxDefault(getTeacherEntity(id));
    }

    /**
     * Propagation.REQUIRED
     * 外围方法未开启事务，方法异常
     */
    public void addRequired() {
        String id = "101";
        // 均不影响事务，不会回滚
        userAppService.addRequired(getUserEntity(id));

        teacherAppService.addRequired(getTeacherEntity(id));
        // 外围方法未开启事务，方法异常
        throw new RuntimeException();
    }

    /**
     * Propagation.REQUIRED
     * 外围方法未开启事务
     */
    public void addRequiredException() {
        String id = "102";

        // 不影响，正常插入，后续方法回滚不影响
        userAppService.addRequired(getUserEntity(id));
        // 回滚
        teacherAppService.addRequiredException(getTeacherEntity(id));
    }

    /**
     * Propagation.REQUIRED
     * 外围方法开启事务
     */
    @Transactional
    public void addRequiredExceptionTx() {
        String id = "103";
        /*
         * 外围方法开启事务，内部方法加入外围方法事务，外围方法回滚，内部方法也要回滚
         * 影响内部事务，均会回滚，无法插入
         */
        userAppService.addRequired(getUserEntity(id));

        try {
            teacherAppService.addRequiredException(getTeacherEntity(id));
        } catch (Exception e) {
            System.out.println("==> 方法回滚");
        }
    }

    /**
     * Propagation.REQUIRES_NEW
     * 外围方法未开启事务，方法异常
     */
    public void addRequiredNew() {
        String id = "110";
        // 均不影响事务，不会回滚
        userAppService.addRequiredNew(getUserEntity(id));
        teacherAppService.addRequiredNew(getTeacherEntity(id));
        // 外围方法未开启事务，方法异常
        throw new RuntimeException();
    }

    /**
     * Propagation.REQUIRED
     * 外围方法未开启事务，都在各自独立的事务运行
     */
    public void addRequiredNewException() {
        String id = "111";
        // 不影响，正常插入，后续方法回滚不影响
        userAppService.addRequiredNew(getUserEntity(id));
        // 回滚
        teacherAppService.addRequiredNewException(getTeacherEntity(id));
    }

    /**
     * Propagation.REQUIRED
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredNewTx001() {
        String id = "112";

        // 和外围方法一个事务，外围方法抛出异常只回滚和外围方法同一事务的方法
        // 插入失败，回滚
        userAppService.addRequired(getUserEntity(id));
        id = "113";
        // 独立的新建事务，不影响，正常插入
        userAppService.addRequiredNew(getUserEntity(id));
        // 独立的新建事务，不影响，正常插入
        teacherAppService.addRequiredNew(getTeacherEntity(id));
        // 外围方法未开启事务，方法异常,影响外围事务
        throw new RuntimeException();
    }

    /**
     * Propagation.REQUIRED
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredNewTx002() {
        String id = "114";
        // 和外围方法一个事务
        // 插入失败，回滚
        userAppService.addRequired(getUserEntity(id));
        id = "115";
        // 独立的新建事务，不影响，正常插入
        userAppService.addRequiredNew(getUserEntity(id));
        // 独立的新建事务，异常回滚。
        // 异常继续抛出被外围方法感知，外围方法事务亦被回滚
        teacherAppService.addRequiredNewException(getTeacherEntity(id));
    }

    /**
     * Propagation.REQUIRED
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredNewTx003() {

        String id = "116";
        // 和外围方法一个事务
        // 正常插入
        userAppService.addRequired(getUserEntity(id));
        id = "117";
        // 独立的新建事务，不影响，正常插入
        userAppService.addRequiredNew(getUserEntity(id));

        try {
            // 独立的新建事务，异常回滚。
            // 异常抛出，在外围方法已捕获和处理，不继续抛出，异常被catch不会被外围方法感知 ，外围方法事务不会回滚
            teacherAppService.addRequiredNewException(getTeacherEntity(id));
        } catch (Exception e) {
            System.out.println("==> 方法回滚");
        }
    }

    /**
     * Propagation.NESTED
     * 外围方法未开启事务，方法异常
     */
    public void addRequiredNested() {
        String id = "120";
        // 均不影响事务，不会回滚
        userAppService.addRequiredNested(getUserEntity(id));
        teacherAppService.addRequiredNested(getTeacherEntity(id));
        // 外围方法未开启事务，方法异常
        throw new RuntimeException();
    }

    /**
     * Propagation.NESTED
     * 外围方法未开启事务
     */
    public void addRequiredNestedException() {
        String id = "121";
        // 不影响，正常插入，后续方法回滚不影响
        userAppService.addRequiredNested(getUserEntity(id));
        // 回滚
        teacherAppService.addRequiredNestedException(getTeacherEntity(id));
    }

    /**
     * Propagation.NESTED
     * 外围方法未开启事务，方法异常
     */
    @Transactional
    public void addRequiredNestedTx001() {
        String id = "122";

        userAppService.addRequiredNested(getUserEntity(id));
        teacherAppService.addRequiredNested(getTeacherEntity(id));
        // 外围方法未开启事务，方法异常
        throw new RuntimeException();
    }

    /**
     * Propagation.NESTED
     * 外围方法未开启事务，方法异常
     */
    @Transactional
    public void addRequiredNestedTx002() {
        String id = "123";
        userAppService.addRequiredNested(getUserEntity(id));
        teacherAppService.addRequiredNestedException(getTeacherEntity(id));
    }

    /**
     * Propagation.NESTED
     * 外围方法未开启事务，方法异常
     */
    @Transactional
    public void addRequiredNestedTx003() {
        String id = "124";
        userAppService.addRequiredNested(getUserEntity(id));

        try {
            //
            teacherAppService.addRequiredNestedException(getTeacherEntity(id));
        } catch (Exception e) {
            System.out.println("==> 方法回滚");
        }
    }

    private UserEntity getUserEntity(String id) {
        UserEntity entity = new UserEntity();
        entity.setName(UT_PREFX_USER + id);
        return entity;
    }

    private TeacherEntity getTeacherEntity(String id) {
        TeacherEntity entity = new TeacherEntity();
        entity.setName(UT_PREFX_TEACHER + id);
        return entity;
    }
}
