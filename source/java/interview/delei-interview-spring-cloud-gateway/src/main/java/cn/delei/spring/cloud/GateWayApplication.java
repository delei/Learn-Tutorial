package cn.delei.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Application 入口
 *
 * @author deleiguo
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {

    /**
     * Main方式启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // Spring Boot 启动入口
        SpringApplication.run(GateWayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route_2", r -> r.path("/hello")
                        .uri("http://localhost:8081/hello"))
                .build();
    }
}