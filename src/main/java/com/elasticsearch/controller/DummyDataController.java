package com.elasticsearch.controller;

import com.elasticsearch.service.DummyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DummyDataController {

    private final DummyDataService dummyDataService;

    @PostMapping("/dummy/{indexName}")
    public ResponseEntity<Boolean> insertDummyData(@PathVariable("indexName") String indexName) {
        return ResponseEntity.ok().body(dummyDataService.insertDummyData(indexName));
    }
}
