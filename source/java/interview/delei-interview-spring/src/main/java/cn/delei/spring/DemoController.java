package cn.delei.spring;

import cn.delei.spring.autoconfigure.DeleiConfigureProperties;
import cn.delei.spring.ioc.IOCDemoService;
import cn.delei.spring.starter.DeleiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Demo Controller
 *
 * @author deleiguo
 */
@RestController
@RequestMapping(value = "/")
public class DemoController {
    @Resource
    private IOCDemoService iocDemoServiceDefaultImpl;
    @Resource
    private DeleiService deleiService;
    @Resource
    private DeleiConfigureProperties deleiConfigureProperties;

    @Value("${delei.config.name}")
    private String configName;

    @GetMapping(value = "/ioc")
    public String iocMethod() {
        iocDemoServiceDefaultImpl.print();
        return "Success";
    }

    @GetMapping(value = "/config")
    public String configMethod() {
        // @Value读取配置文件
        System.out.println("@value configName: " + configName);
        // @ConfigurationProperties注解读取配置文件
        System.out.println("@ConfigurationProperties: " + deleiConfigureProperties.toString());
        // 自定义starter中的读取jar中的配置文件
        deleiService.loadDelei();
        return "Success";
    }

}
