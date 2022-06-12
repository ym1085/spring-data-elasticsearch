package com.elasticsearch.service;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.VehicleRequest;
import com.elasticsearch.helper.IndicesHelper;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.search.util.SearchUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient client;

    public Boolean saveDocumentToIndex(VehicleRequest.request request) {
        Vehicle vehicleEntity = request.toEntity();
        try {
            String vehicleAsString = mapper.writeValueAsString(vehicleEntity);
            log.debug("vehicleAsString = {}", vehicleAsString);

            IndexRequest indexRequest = new IndexRequest(IndicesHelper.VEHICLE_INDEX_NAME);
            indexRequest.id(vehicleEntity.getId());
            indexRequest.source(vehicleAsString, XContentType.JSON); // set document source to index

            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.CREATED);
        } catch(Exception e) {
            log.error("failed to save Vehicle Document to index", e);
            return false;
        }
    }

    public Vehicle findVehicleById(String vehicleId) {
        try {
            GetResponse response = client.get(new GetRequest(IndicesHelper.VEHICLE_INDEX_NAME, vehicleId), RequestOptions.DEFAULT);
            log.debug("response = {}", response.toString());

            if (response.isSourceEmpty()) {
                return new Vehicle();
            }
            return mapper.readValue(response.getSourceAsString(), Vehicle.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    // FIXME: 중복 메서드 공통화 필요
    public List<Vehicle> searchVehicleByMatchAll() {
        SearchRequest request = SearchUtil.buildSearchRequestByMatchAllQuery(IndicesHelper.VEHICLE_INDEX_NAME);
        return  searchInternal(request);
    }

    public List<Vehicle> searchVehiclesByMatchQuery(SearchRequestDto searchRequestDto) {
        SearchRequest request = SearchUtil.buildSearchRequestByMatchQuery(IndicesHelper.VEHICLE_INDEX_NAME, searchRequestDto);
        return searchInternal(request);
    }

    public List<Vehicle> searchVehiclesByRangeQuery(Date date) {
        SearchRequest request = SearchUtil.buildSearchRequestByRangeQuery(IndicesHelper.VEHICLE_INDEX_NAME, "created_at", date);
        return searchInternal(request);
    }

    public List<Vehicle> searchVehiclesByBoolQuery(SearchRequestDto searchRequestDto, Date date) {
        SearchRequest request = SearchUtil.buildSearchRequestByBoolQuery(IndicesHelper.VEHICLE_INDEX_NAME, searchRequestDto, date);
        return searchInternal(request);
    }

    private List<Vehicle> searchInternal(SearchRequest request) {
        if (request == null) {
            log.error("Failed to build search request query");
            return Collections.emptyList();
        }

        List<Vehicle> vehicle = null;
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("response = {}", response.toString());

            SearchHit[] searchHits = response.getHits().getHits(); // hits : [ { key : value } ]
            log.debug("searchHits = {}", searchHits.length);
            vehicle = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                vehicle.add(mapper.readValue(hit.getSourceAsString(), Vehicle.class)); // json to string
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicle;
    }
}
