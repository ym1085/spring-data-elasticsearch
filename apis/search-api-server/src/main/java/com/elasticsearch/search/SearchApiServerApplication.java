package com.elasticsearch.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.elasticsearch"})
public class SearchApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApiServerApplication.class, args);
    }
}
