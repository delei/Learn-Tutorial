package cn.delei.spring.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DeleiAutoConfiguration 自动装载
 * spring.factories文件
 * @author deleiguo
 */
@Configuration
@ConditionalOnBean(annotation = EnableDeleiConfiguration.class)
@EnableConfigurationProperties(DeleiProperties.class)
public class DeleiAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DeleiService deleiService() {
        return new DeleiServiceImpl();
    }
}
