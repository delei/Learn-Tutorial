package cn.delei.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/")
public class IOCDemoController {

    @Resource
    private IOCDemoService iocDemoServiceDefaultImpl;

    @GetMapping(value = "/ioc")
    public String defaultMethod() {
        iocDemoServiceDefaultImpl.print();
        return "Success";
    }

}
