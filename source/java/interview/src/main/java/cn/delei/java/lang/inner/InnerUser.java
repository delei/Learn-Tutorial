package cn.delei.java.lang.inner;

import java.io.Serializable;

public class InnerUser implements Serializable {
    private String name;
    private int age;
    private transient String password;

    public InnerUser() {
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
}
