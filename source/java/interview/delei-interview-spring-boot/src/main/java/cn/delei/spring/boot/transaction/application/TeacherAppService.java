package cn.delei.spring.boot.transaction.application;

import cn.delei.spring.boot.transaction.domain.teacher.entity.TeacherEntity;
import cn.delei.spring.boot.transaction.domain.teacher.service.impl.TxTeacherDomainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherAppService")
public class TeacherAppService {

    @Autowired
    private TxTeacherDomainServiceImpl txTeacherDomainService;

    @Transactional
    public void addTxDefault(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
        throw new RuntimeException("==> addRequired Exception");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
        throw new RuntimeException("==> addRequired Exception");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiredNew(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiredNewException(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
        throw new RuntimeException("==> addRequired Exception");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addRequiredNested(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addRequiredNestedException(TeacherEntity entity) {
        txTeacherDomainService.add(entity);
        throw new RuntimeException("==> addRequiredNested Exception");
    }
}
