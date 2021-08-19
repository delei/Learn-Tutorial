package cn.delei.spring.boot;

import cn.delei.spring.boot.mybatis.dao.StudentMapper;
import cn.delei.spring.boot.mybatis.entity.StudentEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@MapperScan("cn.delei.spring.boot.mybatis.**.dao")
@SpringBootApplication
public class BootApplication {

    /**
     * Main方式启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(BootApplication.class, args);
        test(context);
    }

    static void test(ApplicationContext context) {
        StudentMapper studentMapper = context.getBean("studentMapper", StudentMapper.class);
        List<StudentEntity> dataList = studentMapper.selectByName(null);
        dataList.forEach(s -> System.out.println(s.toString()));
    }
}