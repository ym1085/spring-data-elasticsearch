package com.elasticsearch.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.elasticsearch"})
public class ElasticClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticClientApplication.class, args);
    }
}
