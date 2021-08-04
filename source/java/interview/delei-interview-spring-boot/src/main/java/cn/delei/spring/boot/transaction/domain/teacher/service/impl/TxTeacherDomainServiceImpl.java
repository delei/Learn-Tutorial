package cn.delei.spring.boot.transaction.domain.teacher.service.impl;

import cn.delei.spring.boot.transaction.domain.teacher.entity.TeacherEntity;
import cn.delei.spring.boot.transaction.domain.teacher.repository.TeacherRepository;
import cn.delei.spring.boot.transaction.domain.teacher.service.TxTeacherDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service("txTeacherDomainServiceImpl")
public class TxTeacherDomainServiceImpl implements TxTeacherDomainService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void add(TeacherEntity entity) {
        teacherRepository.add(entity);
    }

    @Override
    public void update(TeacherEntity entity) {
        teacherRepository.update(entity);
    }

    @Override
    public TeacherEntity selectByPrimaryKey(@NotNull Long key) {
        return teacherRepository.selectByPrimaryKey(key);
    }

    @Override
    public void deleteByPrimaryKey(@NotNull Long key) {
        teacherRepository.deleteByPrimaryKey(key);
    }
}
