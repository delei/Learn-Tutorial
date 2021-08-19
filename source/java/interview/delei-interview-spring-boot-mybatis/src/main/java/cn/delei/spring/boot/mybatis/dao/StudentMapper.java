package cn.delei.spring.boot.mybatis.dao;

import cn.delei.spring.boot.mybatis.entity.StudentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Student Mapper
 *
 * @author deleiguo
 */
public interface StudentMapper {
    void insert(StudentEntity studentEntity);

    void update(StudentEntity studentEntity);

    void deleteByPk(Long pk);

    StudentEntity selectByPk(Long pk);

    List<StudentEntity> selectByName(@Param("name") String name);
}
