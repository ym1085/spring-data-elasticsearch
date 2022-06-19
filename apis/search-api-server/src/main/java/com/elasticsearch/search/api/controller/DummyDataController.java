package com.elasticsearch.search.api.controller;

import com.elasticsearch.core.service.DummyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DummyDataController {

    private final DummyDataService dummyDataService;

    @PostMapping("/dummy/{indexName}")
    public ResponseEntity<Boolean> insertDummyData(@PathVariable("indexName") String indexName) {
        return ResponseEntity.ok().body(dummyDataService.insertDummyData(indexName));
    }
}
