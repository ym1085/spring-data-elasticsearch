package com.elasticsearch.search.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public String intro(@RequestParam String title) {
        String introduction = "Welcome to " + title + " intro.";
        return introduction;
    }
}
