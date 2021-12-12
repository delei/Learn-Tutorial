package cn.delei.spring.cloud.remote.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO 类：老师
 *
 * @author deleiguo
 */
public class RemoteTeacher implements Serializable {
    private static final long serialVersionUID = 4059356381304932851L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;

    public RemoteTeacher() {
    }

    public RemoteTeacher(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int hashCode() {
        int nameHash = name.toUpperCase().hashCode();
        return nameHash ^ age;
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof RemoteTeacher) {
            final RemoteTeacher p = (RemoteTeacher) obj;
            return p.getName().equals(this.getName())
                    && p.getAge() == this.getAge();
        }
        return false;
    }
}
