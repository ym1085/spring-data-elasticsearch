package com.elasticsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @GetMapping(value = "/test")
    public String intro(@RequestParam String title) {
        String introduction = "Welcome to " + title + " intro.";
        return introduction;
    }
}
