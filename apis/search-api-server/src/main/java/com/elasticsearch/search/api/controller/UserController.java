package com.elasticsearch.search.api.controller;

import com.elasticsearch.core.document.User;
import com.elasticsearch.core.search.dto.req.UserRequestDto;
import com.elasticsearch.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> retrieveByUserId(@PathVariable("id") String id) {
        log.info("id = {}", id);

        if (StringUtils.isNotBlank(id)) {
           //TODO: ErrorResponse code
        }
        return ResponseEntity.ok().body(userService.retrieveByUserId(id));
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Boolean> saveUserInfo(@RequestBody @Valid UserRequestDto.request userRequestDto, BindingResult result) {
        log.info("userRequestDto = {}", userRequestDto.toString());

        if (result.hasErrors()) {
            log.error("Invalid request datas");
            //TODO: ErrorResponse code
        }

        return ResponseEntity.ok().body(userService.saveUserInfo(userRequestDto));
    }
}
