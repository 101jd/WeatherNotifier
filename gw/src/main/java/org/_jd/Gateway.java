package org._jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@EnableConfigurationProperties
@SpringBootApplication
public class Gateway {
    @Bean
    RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        predicateSpec ->
                            predicateSpec.path("/reg")
                                    .filters(gatewayFilterSpec ->
                                            gatewayFilterSpec.addRequestHeader(
                                                    "name", "reg"
                                            ))
                                    .uri("http://127.0.0.1:8081")

                )
                .route(
                        predicateSpec ->
                                predicateSpec.path("/rem")
                                        .filters(gatewayFilterSpec ->
                                                gatewayFilterSpec.addRequestHeader(
                                                        "name", "rem"
                                                ))
                                        .uri("http://127.0.0.1:8082")
                )
                .route(
                        predicateSpec ->
                                predicateSpec.path("/not")
                                        .filters(gatewayFilterSpec ->
                                                gatewayFilterSpec.addRequestHeader(
                                                        "name", "not"
                                                ))
                                        .uri("http://127.0.0.1:8083")
                ).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }

}