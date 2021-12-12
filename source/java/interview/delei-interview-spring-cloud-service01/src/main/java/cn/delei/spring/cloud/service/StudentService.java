package cn.delei.spring.cloud.service;

import cn.delei.pojo.Person;

import java.util.List;

public interface StudentService {

    Person selectSingle();

    List<Person> personList();
}
