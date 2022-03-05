package com.elasticsearch.service;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.search.SearchRequestDto;

import java.util.Date;
import java.util.List;

public interface VehicleService {

    Boolean saveDocumentToIndex(VehicleRequestDto vehicle);

    Vehicle findById(String id);

    List<Vehicle> searchContentsByMatchQuery(SearchRequestDto searchRequestDto);

    List<Vehicle> searchContentsByRangeQuery(Date date);

    List<Vehicle> searchContentsByBoolQuery(SearchRequestDto searchRequestDto, Date date);
}
