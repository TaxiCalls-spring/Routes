package com.taxicalls.routes.model;

import java.util.logging.Logger;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EntityScan("com.taxicalls.routes.model")
@EnableJpaRepositories("com.taxicalls.routes.model")
@PropertySource("classpath:db-config.properties")
public class RoutesConfiguration {

    protected Logger logger;

    public RoutesConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

}
