package com.elasticsearch.service;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.search.SearchRequestDto;

import java.util.List;

public interface VehicleService {

    Boolean createDocumentToIndex(Vehicle vehicle);

    Vehicle findById(String id);

    List<Vehicle> search(SearchRequestDto searchRequestDto);
}
