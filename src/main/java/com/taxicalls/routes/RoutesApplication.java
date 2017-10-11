package com.taxicalls.routes;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.taxicalls.routes.model.RoutesConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.taxicalls.routes.model.RoutesRepository;

@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
@Import(RoutesConfiguration.class)
public class RoutesApplication {

    @Autowired
    protected RoutesRepository routesRepository;

    protected Logger logger = Logger.getLogger(RoutesApplication.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(RoutesApplication.class, args);
    }
}
