package com.elasticsearch.controller;

import com.elasticsearch.document.User;
import com.elasticsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/user")
    public void save(@RequestBody final User user) {
        log.info("user = {}", user.toString());
        userService.save(user);
    }
}
