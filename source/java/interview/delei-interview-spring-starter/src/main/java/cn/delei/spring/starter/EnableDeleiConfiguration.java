package cn.delei.spring.starter;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 在入口 main 类中，加上该注解，才能生效
 * @author deleiguo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableDeleiConfiguration {
}
