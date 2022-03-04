package com.elasticsearch.controller;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.service.VehicleDummyDataService;
import com.elasticsearch.service.VehicleService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final Logger log = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;
    private final VehicleDummyDataService dummyDataService;

    /**
     * 인덱스 안에 도큐먼트 생성
     *
     * @desc 도큐먼트 생성 요청
     * @param vehicle
     */
    @PostMapping
    public void createDocument(@RequestBody final Vehicle vehicle) {
        log.info("vehicle = {}", vehicle);
        vehicleService.createDocumentToIndex(vehicle);
    }

    /**
     * @desc Dummy 데이터 생성
     */
    @PostMapping("/insertDummyData")
    public void insertDummyData() {
        dummyDataService.insertDummyDataToIndex();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable final String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(vehicleService.findById(id), HttpStatus.OK);
    }

    /**
     * @desc Multi Match or Match query 검색
     */
    @PostMapping("/search")
    public List<Vehicle> searchByMatchOrMultiMatchQuery(@RequestBody final SearchRequestDto searchRequestDto) {
        log.info("searchRequestDto = {}", searchRequestDto.toString());
        return vehicleService.searchByMatchOrMultiMatchQuery(searchRequestDto);
    }

    /**
     * @desc date 이후에 생성된 모든 데이터 조회
     */
    @GetMapping(value = "/search/{date}")
    public List<Vehicle> searchByDate(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("date = {}", date.toString());
        return vehicleService.searchByDate(date);
    }

    @PostMapping(value = "/search/{date}")
    public List<Vehicle> searchByContentsAndDate(
        @RequestBody final SearchRequestDto searchRequestDto,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        log.info("searchRequestDto = {}, Date = {}", searchRequestDto.toString(), date.toString());
        return vehicleService.searchByContentsAndDate(searchRequestDto, date);
    }
}
