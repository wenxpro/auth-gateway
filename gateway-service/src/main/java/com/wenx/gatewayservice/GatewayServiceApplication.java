package com.wenx.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("resource", r -> r.path("/resource/**")
                        .filters(f -> f.stripPrefix(1))//去掉第一层前缀如果是/api/oauth这种 就stripPrefix(2)
                        .uri("lb://resource-service")) // Prevents cookie being sent downstream
//                        .uri("http://localhost:9090")) // Taking advantage of docker naming
                .route("uaa",r -> r.path("/uaa/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://uaa-service"))
                .build();
    }
}
