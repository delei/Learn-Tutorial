package cn.delei.spring.cloud.service.impl;

import cn.delei.pojo.Teacher;
import cn.delei.spring.cloud.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public Teacher selectSingle() {
        return new Teacher("Delei", 20);
    }

    @Override
    public List<Teacher> teacherList() {
        List<Teacher> dataList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            dataList.add(new Teacher("T" + i, i));
        }
        return dataList;
    }
}
