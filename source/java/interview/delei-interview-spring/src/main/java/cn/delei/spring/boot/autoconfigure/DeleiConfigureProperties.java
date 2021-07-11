package cn.delei.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动配置文件
 * @author deleiguo
 */
@Component
@ConfigurationProperties(prefix = "delei.model")
public class DeleiConfigureProperties {

    private String name;
    private String age;
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "DeleiConfigureProperties{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
