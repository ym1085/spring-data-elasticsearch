package com.elasticsearch.controller;

import com.elasticsearch.service.DummyDataService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DummyDataController {
    private static final Logger LOG = LoggerFactory.getLogger(DummyDataController.class);

    private final DummyDataService dummyDataService;

    @PostMapping("/dummy/{indexName}")
    public ResponseEntity<Boolean> insertDummyData(@PathVariable("indexName") String indexName) {
        LOG.info("indexName = {}", indexName);
        return ResponseEntity.ok().body(dummyDataService.insertDummyData(indexName));
    }
}
