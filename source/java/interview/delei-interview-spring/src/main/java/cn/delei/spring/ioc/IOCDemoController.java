package cn.delei.spring.ioc;

import cn.delei.spring.starter.DeleiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/")
public class IOCDemoController {
    @Resource
    private IOCDemoService iocDemoServiceDefaultImpl;
    @Resource
    private DeleiService deleiService;

    @GetMapping(value = "/ioc")
    public String defaultMethod() {
        iocDemoServiceDefaultImpl.print();
        return "Success";
    }

    @GetMapping(value = "/config")
    public String configMethod() {
        deleiService.loadDelei();
        return "Success";
    }

}
