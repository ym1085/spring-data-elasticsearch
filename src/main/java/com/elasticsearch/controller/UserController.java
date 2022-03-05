package com.elasticsearch.controller;

import com.elasticsearch.document.User;
import com.elasticsearch.dto.request.UserRequestDto;
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

    @GetMapping(value = "/user")
    public ResponseEntity<User> findById(@RequestBody final UserRequestDto userRequestDto) {
        log.info("userRequestDto = {}", userRequestDto.toString());
        return ResponseEntity.ok().body(userService.findById(userRequestDto));
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Boolean> saveDocumentToIndex(@RequestBody final UserRequestDto userRequestDto) {
        log.info("userRequestDto = {}", userRequestDto.toString());
        return ResponseEntity.ok().body(userService.saveDocumentToIndex(userRequestDto));
    }
}
