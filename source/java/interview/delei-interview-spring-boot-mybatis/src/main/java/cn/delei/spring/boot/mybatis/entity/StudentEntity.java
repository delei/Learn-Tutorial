package cn.delei.spring.boot.mybatis.entity;

import cn.delei.spring.boot.mybatis.BaseConstants;

/**
 * Student Entity
 *
 * @author deleiguo
 */
public class StudentEntity {

    private Long id;
    private String name;
    private int sex = BaseConstants.SEX_MAN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public StudentEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentEntity(Long id, String name, int sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
