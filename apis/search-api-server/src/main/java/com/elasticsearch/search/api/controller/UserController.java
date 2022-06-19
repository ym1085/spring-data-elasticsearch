package com.elasticsearch.search.api.controller;

import com.elasticsearch.core.document.User;
import com.elasticsearch.core.dto.UserRequestDto;
import com.elasticsearch.core.service.UserService;
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
    public ResponseEntity<User> findById(@RequestBody UserRequestDto.request userRequestDto) {
        log.info("userRequestDto = {}", userRequestDto.toString());
        return ResponseEntity.ok().body(userService.findById(userRequestDto));
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Boolean> saveDocumentToIndex(@RequestBody UserRequestDto.request userRequestDto) {
        log.info("userRequestDto = {}", userRequestDto.toString());
        return ResponseEntity.ok().body(userService.saveDocumentToIndex(userRequestDto));
    }
}
