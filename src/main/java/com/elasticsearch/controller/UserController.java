package com.elasticsearch.controller;

import com.elasticsearch.document.User;
import com.elasticsearch.dto.UserRequest;
import com.elasticsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user")
    public ResponseEntity<User> findById(@RequestBody UserRequest.request request) {
        log.info("request = {}", request.toString());
        return ResponseEntity.ok().body(userService.findById(request));
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Boolean> saveDocumentToIndex(@RequestBody UserRequest.request request) {
        log.info("request = {}", request.toString());
        return ResponseEntity.ok().body(userService.saveDocumentToIndex(request));
    }
}
