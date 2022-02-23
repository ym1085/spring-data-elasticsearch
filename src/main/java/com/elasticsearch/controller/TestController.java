package com.elasticsearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/test")
    public String intro(@RequestParam String title) {
        logger.info("title = {}", title);
        String introduction = "Welcome to " + title + " intro.";
        return introduction;
    }
}
