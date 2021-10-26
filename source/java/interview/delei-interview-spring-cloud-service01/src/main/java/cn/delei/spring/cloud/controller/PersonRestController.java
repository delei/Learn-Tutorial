package cn.delei.spring.cloud.controller;

import cn.delei.pojo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Person WebFlux Controller
 *
 * @author deleiguo
 */
@RestController
@RequestMapping("/person")
public class PersonRestController {

    @GetMapping("/")
    public Mono<String> hello() {
        return Mono.just("Hello PersonRestController");
    }

    @GetMapping("/{id}")
    public Mono<Person> findPersonById(@PathVariable int id) {
        return Mono.justOrEmpty(new Person("Delei", 20, null));
    }

    @GetMapping("/list")
    public Flux<Person> personList() throws InterruptedException {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            personList.add(new Person("P" + i, i, null));
        }
        return Flux.fromIterable(personList);
    }
}
