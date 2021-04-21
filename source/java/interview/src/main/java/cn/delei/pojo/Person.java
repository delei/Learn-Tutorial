package cn.delei.pojo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * POJO 类
 *
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

    public Person(String name, int age, String password) {
        super();
        this.name = name;
        this.age = age;
        this.password = password;
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

        if (obj instanceof Person) {
            final Person p = (Person) obj;
            return this.getName().equals(p.getName())
                    && this.getAge() == p.getAge();
        }

        return false;
    }
}
