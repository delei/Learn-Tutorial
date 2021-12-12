package cn.delei.spring.cloud.service;

import cn.delei.pojo.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher selectSingle();

    List<Teacher> teacherList();
}
