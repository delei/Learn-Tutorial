package cn.delei.spring.boot.webflux;

import cn.delei.pojo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Web Controller Demo
 *
 * @author deleiguo
 */
@RestController
public class WebRestController {

    @GetMapping("/web/hello")
    public String hello() {
        return "Hello WebFlux";
    }

    @GetMapping("/web/person/{id}")
    public Person findPersonById(@PathVariable int id) {
        return new Person("Delei", 20, null);
    }

    @GetMapping("/web/person/list")
    public List<Person> personList() throws InterruptedException {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Thread.sleep(100);
            personList.add(new Person("P" + i, i, null));
        }
        return personList;
    }
}
