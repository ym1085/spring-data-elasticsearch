package com.elasticsearch.controller;

import com.elasticsearch.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final IndexService indexService;

    /**
     * 인덱스 재생성
     */
    @PostMapping("/recreate")
    public void recreateAllIndices() {
        indexService.recreateIndices(true);
    }
}
