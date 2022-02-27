package com.elasticsearch.controller;

import com.elasticsearch.document.Person;
import com.elasticsearch.document.Vehicle;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.service.PersonService;
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

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    @PostMapping
    public void createDocumentToIndex(@RequestBody final Vehicle vehicle) {
        log.info("vehicle = {}", vehicle);
        vehicleService.createDocumentToIndex(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(vehicleService.findById(id), HttpStatus.OK);
    }

    /**
     * Multi Match or Match query 검색
     */
    @PostMapping("/search")
    public List<Vehicle> searchByMatchQuery(@RequestBody final SearchRequestDto searchRequestDto) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchByMatchQuery(searchRequestDto);
    }

    /**
     * date 이후에 생성된 모든 데이터 조회
     */
    @GetMapping("/search/{date}")
    public List<Vehicle> searchByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("date = {}", date.toString());
        return vehicleService.searchByDate(date);
    }
}
