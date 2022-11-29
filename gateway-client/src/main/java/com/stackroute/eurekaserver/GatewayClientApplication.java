package com.stackroute.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayClientApplication.class, args);
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


