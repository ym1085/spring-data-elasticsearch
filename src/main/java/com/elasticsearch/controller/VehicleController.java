package com.elasticsearch.controller;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.VehicleRequest;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(value = "/vehicle")
    public void saveDocumentToIndex(@RequestBody VehicleRequest.request vehicleRequestDto) {
        log.info("vehicle = {}", vehicleRequestDto.toString());
        vehicleService.saveDocumentToIndex(vehicleRequestDto);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable String id) {
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
    public List<Vehicle> searchVehiclesByMatchQuery(@RequestBody SearchRequestDto searchRequestDto) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchVehiclesByMatchQuery(searchRequestDto);
    }

    @GetMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchVehiclesByRangeQuery(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("date = {}", date.toString());
        return vehicleService.searchVehiclesByRangeQuery(date);
    }

    @PostMapping(value = "/vehicle/search/{date}")
    public List<Vehicle> searchVehiclesByBoolQuery(
            @RequestBody SearchRequestDto searchRequestDto,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchVehiclesByBoolQuery(searchRequestDto, date);
    }
}
