package cn.delei.spring.cloud.service.impl;

import cn.delei.pojo.Person;
import cn.delei.spring.cloud.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImps implements StudentService {
    @Override
    public Person selectSingle() {
        return new Person("Delei", 20, null);
    }

    @Override
    public List<Person> personList() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            personList.add(new Person("P" + i, i, null));
        }
        return personList;
    }
}
