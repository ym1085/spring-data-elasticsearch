package com.elasticsearch.service;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.search.SearchRequestDto;

import java.util.Date;
import java.util.List;

public interface VehicleService {

    Boolean saveDocumentToIndex(VehicleRequestDto vehicle);

    Vehicle findVehicleById(String id);

    List<Vehicle> searchVehicleByMatchAll();

    List<Vehicle> searchVehiclesByMatchQuery(SearchRequestDto searchRequestDto);

    List<Vehicle> searchVehiclesByRangeQuery(Date date);

    List<Vehicle> searchVehiclesByBoolQuery(SearchRequestDto searchRequestDto, Date date);
}
