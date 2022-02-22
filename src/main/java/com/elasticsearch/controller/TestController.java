package com.elasticsearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/test")
    public ResponseEntity<?> intro(@RequestBody Map<String, Object> paramMap) {
        logger.info("paramMap = {}", paramMap.toString());
        String introduction = "Welcome to " + paramMap.get("title") + "intro.";
        return ResponseEntity.ok().body(introduction);
    }
}
