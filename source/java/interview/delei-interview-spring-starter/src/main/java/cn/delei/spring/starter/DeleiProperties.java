package cn.delei.spring.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 读取自定义配置属性
 * @author deleiguo
 */
@ConfigurationProperties(prefix = "delei.dg")
public class DeleiProperties {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DeleiProperties{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
