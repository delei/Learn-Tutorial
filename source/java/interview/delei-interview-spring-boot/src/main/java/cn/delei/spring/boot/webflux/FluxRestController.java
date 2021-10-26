package cn.delei.spring.boot.webflux;

import cn.delei.pojo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * WebFlux Controller Demo
 *
 * @author deleiguo
 */
@RestController
public class FluxRestController {

    @GetMapping("/webflux/hello")
    public Mono<String> hello() {
        return Mono.just("Hello WebFlux");
    }

    @GetMapping("/webflux/person/{id}")
    public Mono<Person> findPersonById(@PathVariable int id) {
        return Mono.justOrEmpty(new Person("Delei", 20, null));
    }

    @GetMapping("/webflux/person/list")
    public Flux<Person> personList() throws InterruptedException {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Thread.sleep(100);
            personList.add(new Person("P" + i, i, null));
        }
        return Flux.fromIterable(personList);
    }
}
