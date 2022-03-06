package com.elasticsearch.controller;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.service.DummyDataService;
import com.elasticsearch.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 차량 정보 관련 검색 컨트롤러 입니다.
 *
 * @author ymkim
 * @since 2022.03.05 Sun 13:15
 *
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleController {
    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    @PostMapping(value = "/vehicle")
    public void saveDocumentToIndex(@RequestBody final VehicleRequestDto vehicleRequestDto) {
        log.info("vehicle = {}", vehicleRequestDto.toString());
        vehicleService.saveDocumentToIndex(vehicleRequestDto);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(vehicleService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/vehicle/search")
    public List<Vehicle> searchContentsByMatchQuery(@RequestBody final SearchRequestDto searchRequestDto) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchContentsByMatchQuery(searchRequestDto);
    }

    @GetMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchContentsByRangeQuery(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("date = {}", date.toString());
        return vehicleService.searchContentsByRangeQuery(date);
    }

    @PostMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchContentsByBoolQuery(
        @RequestBody final SearchRequestDto searchRequestDto,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("searchRequestDto = {}, Date = {}", searchRequestDto.toString(), date.toString());
        return vehicleService.searchContentsByBoolQuery(searchRequestDto, date);
    }
}
