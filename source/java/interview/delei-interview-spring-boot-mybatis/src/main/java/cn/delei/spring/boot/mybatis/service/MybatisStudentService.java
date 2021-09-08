package cn.delei.spring.boot.mybatis.service;

import cn.delei.spring.boot.mybatis.dao.StudentMapper;
import cn.delei.spring.boot.mybatis.entity.StudentEntity;
import cn.delei.util.PrintUtil;
import cn.hutool.core.lang.Assert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MybatisStudentService {
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentEntity> selectByName(String name) {
        Assert.notEmpty(name, "name is null");
        return studentMapper.selectByName(name);
    }

    @Transactional
    public void selectByNameCacheWithTransactional(String name) {
        selectByNameCacheDefault(name);
    }

    public void selectByNameCacheDefault(String name) {
        List<StudentEntity> dataList = selectByName(name);
        dataList.forEach(s -> System.out.println(s.toString()));
        PrintUtil.printDivider();
        dataList = selectByName(name);
        dataList.forEach(s -> System.out.println(s.toString()));
    }

    public PageInfo<StudentEntity> selectPageListByName(String name) {
        PageHelper.startPage(1, 2);
        List<StudentEntity> dataList = selectByName(name);
        return new PageInfo<>(dataList);
    }


}
