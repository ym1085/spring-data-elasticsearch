package com.elasticsearch.search.api.controller;

import com.elasticsearch.core.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/index")
    public ResponseEntity<String> recreateAllIndices(@RequestBody Map<String, Object> paramMap) {
        log.info("paramMap.toString = {}", paramMap.toString());
        Map<String, Object> requestParamMap = new LinkedHashMap<>();
        requestParamMap.put("isRecreateIndexYn", true);
        requestParamMap.put("deleteIndexName", paramMap.get("deleteIndexName"));

//        TODO: Response Error Message
        if (StringUtils.isNotBlank(String.valueOf(paramMap.get("deleteIndexName")))) {
            indexService.recreateIndices(requestParamMap);
        }
        return ResponseEntity.ok().body("success");
    }
}
