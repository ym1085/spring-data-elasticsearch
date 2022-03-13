package com.elasticsearch.controller;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 차량 정보 관련 검색 컨트롤러 입니다.
 *
 * @author ymkim
 * @since 2022.03.05 Sat 13:15
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleController {
    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    /**
     * Vehicle Document 생성
     *
     * @param vehicleRequestDto
     */
    @PostMapping(value = "/vehicle")
    public void saveDocumentToIndex(@RequestBody final VehicleRequestDto vehicleRequestDto) {
        log.info("vehicle = {}", vehicleRequestDto.toString());
        vehicleService.saveDocumentToIndex(vehicleRequestDto);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(vehicleService.findVehicleById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle")
    public ResponseEntity<List<Vehicle>> searchVehicleByMatchAll() {
        StopWatch stopWatch = StopWatch.createStarted();

        List<Vehicle> vehicleList = vehicleService.searchVehicleByMatchAll();
        log.info("vehicleList = {}", vehicleList.toString());

        stopWatch.getTime(TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().body(vehicleList);
    }

    @PostMapping("/vehicle/search")
    public List<Vehicle> searchVehiclesByMatchQuery(@RequestBody final SearchRequestDto searchRequestDto) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchVehiclesByMatchQuery(searchRequestDto);
    }

    @GetMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchVehiclesByRangeQuery(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("date = {}", date.toString());
        return vehicleService.searchVehiclesByRangeQuery(date);
    }

    @PostMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchVehiclesByBoolQuery(
        @RequestBody final SearchRequestDto searchRequestDto,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchVehiclesByBoolQuery(searchRequestDto, date);
    }
}
