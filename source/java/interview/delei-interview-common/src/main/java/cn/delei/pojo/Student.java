package cn.delei.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO 类
 *
 * @author deleiguo
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -8402322020446019518L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        super();
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
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
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

        if (obj instanceof Student) {
            final Student p = (Student) obj;
            return this.getName().equals(p.getName())
                    && this.getAge() == p.getAge();
        }

        return false;
    }
}
