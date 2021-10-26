package cn.delei.spring.cloud.controller;

import cn.delei.pojo.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Teacher WebFlux Controller
 *
 * @author deleiguo
 */
@RestController
@RequestMapping("/teacher")
public class TeacherRestController {

    @GetMapping("/")
    public Mono<String> hello() {
        return Mono.just("Hello TeacherRestController");
    }

    @GetMapping("/{id}")
    public Mono<Teacher> findPersonById(@PathVariable int id) {
        return Mono.justOrEmpty(new Teacher("Delei", 20));
    }

    @GetMapping("/list")
    public Flux<Teacher> dataList() throws InterruptedException {
        List<Teacher> dataList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            dataList.add(new Teacher("T" + i, i));
        }
        return Flux.fromIterable(dataList);
    }
}
