package cn.delei.spring.cloud.controller;

import cn.delei.core.R;
import cn.delei.pojo.Person;
import cn.delei.spring.cloud.remote.RemoteTeacherService;
import cn.delei.spring.cloud.remote.model.RemoteTeacher;
import cn.delei.spring.cloud.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Person WebFlux Controller
 *
 * @author deleiguo
 */
@RestController
@RequestMapping("/person")
public class StudentRestController {

    @Resource
    private StudentService personService;

    @Resource
    private RemoteTeacherService remoteTeacherService;

    @GetMapping("/")
    public R<String> hello() {
        return R.ok("Hello PersonRestController");
    }

    @GetMapping("/{id}")
    public R<Person> selectSingle(@PathVariable int id) {
        return R.ok(personService.selectSingle());
    }

    @GetMapping("/list")
    public R<List<Person>> dataList() throws InterruptedException {
        return R.ok(personService.personList());
    }

    @GetMapping("/remote/teachers")
    public R<List<RemoteTeacher>> teacherList() {
        return remoteTeacherService.teacherList();
    }

}
