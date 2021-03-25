package cn.delei.pojo;

import java.io.Serializable;

/**
 * POJO 类
 * @author deleiguo
 */
public class Person implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    private transient String password;

    public Person() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}
