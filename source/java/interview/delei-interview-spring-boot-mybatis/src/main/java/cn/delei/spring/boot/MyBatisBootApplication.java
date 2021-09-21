package cn.delei.spring.boot;

import cn.delei.spring.boot.mybatis.dao.StudentMapper;
import cn.delei.spring.boot.mybatis.entity.StudentEntity;
import cn.delei.spring.boot.mybatis.service.MybatisStudentService;
import cn.delei.spring.starter.DeleiService;
import cn.delei.spring.starter.EnableDeleiConfiguration;
import cn.delei.util.PrintUtil;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@MapperScan("cn.delei.spring.boot.mybatis.**.dao")
@SpringBootApplication
@EnableDeleiConfiguration
public class MyBatisBootApplication {

    /**
     * Main方式启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        ApplicationContext context = SpringApplication.run(MyBatisBootApplication.class, args);
        Map<String, DeleiService> deleiServiceMap = context.getBeansOfType(DeleiService.class);
        deleiServiceMap.entrySet().forEach(e -> {
            e.getValue().loadDelei();
        });
        //querySQLProcess(context);
        //updateSQLProcess(context);
        //cache(context);
        page(context);
    }

    static void querySQLProcess(ApplicationContext context) {
        StudentMapper studentMapper = context.getBean("studentMapper", StudentMapper.class);
        List<StudentEntity> dataList = studentMapper.selectByName("USER");
        dataList.forEach(s -> System.out.println(s.toString()));
    }

    static void updateSQLProcess(ApplicationContext context) {
        StudentMapper studentMapper = context.getBean("studentMapper", StudentMapper.class);
        StudentEntity studentEntity = new StudentEntity(1L, "DeleiGuo");
        studentMapper.update(studentEntity);
    }

    static void cache(ApplicationContext context) {
        MybatisStudentService mybatisStudentService = context.getBean("mybatisStudentService",
                MybatisStudentService.class);
        final String param = "USER";
        PrintUtil.printDivider("不同事务");
        mybatisStudentService.selectByNameCacheDefault(param);
        PrintUtil.printDivider("同一个事务");
        mybatisStudentService.selectByNameCacheWithTransactional(param);
    }

    static void page(ApplicationContext context) {
        MybatisStudentService mybatisStudentService = context.getBean("mybatisStudentService",
                MybatisStudentService.class);
        final String param = "USER";
        PrintUtil.printDivider("pagehelper 分页");
        PageInfo<StudentEntity> pageLists = mybatisStudentService.selectPageListByName(param);
        System.out.println(pageLists.toString());
    }
}