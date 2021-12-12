package cn.delei.spring.cloud.controller;

import cn.delei.core.R;
import cn.delei.pojo.Teacher;
import cn.delei.spring.cloud.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Teacher WebFlux Controller
 *
 * @author deleiguo
 */
@RestController
@RequestMapping("/teacher")
public class TeacherRestController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/")
    public R<String> hello() {
        return R.ok("Hello TeacherRestController");
    }

    @GetMapping("/{id}")
    public R<Teacher> selectSingle(@PathVariable int id) {
        return R.ok(teacherService.selectSingle());
    }

    @GetMapping("/list")
    public R<List<Teacher>> dataList() throws InterruptedException {
        return R.ok(teacherService.teacherList());
    }
}
