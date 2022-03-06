package com.elasticsearch.controller;

import com.elasticsearch.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Elasticsearch의 Index를 생성, 수정, 삭제하기위한 컨트롤러입니다.
 *
 * @author ymkim
 * @since 2022.03.05 Sat 17:38
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final IndexService indexService;

    @GetMapping("/index")
    public ResponseEntity<String> recreateAllIndices(@RequestBody final Map<String, Object> paramMap) {
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
