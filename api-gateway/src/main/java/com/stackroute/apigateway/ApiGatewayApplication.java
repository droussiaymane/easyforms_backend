package com.stackroute.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RouteLocator configureRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("auth", r->r.path("/auth/**").uri("lb://authentication-service"))
				.route("form", r->r.path("/form/**").uri("lb://form-service"))
				.route("user", r->r.path("/user/**").uri("lb://user-service"))
				.build();
	}

}
