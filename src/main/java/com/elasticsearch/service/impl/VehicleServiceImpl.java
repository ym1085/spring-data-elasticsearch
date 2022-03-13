package com.elasticsearch.service.impl;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.helper.IndicesHelper;
import com.elasticsearch.search.SearchRequestDto;
import com.elasticsearch.search.util.SearchUtil;
import com.elasticsearch.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient client;

    /**
     * Vehicle Document 생성
     *
     * @param vehicleRequestDto : [VehicleRequestDto]
     */
    @Override
    public Boolean saveDocumentToIndex(final VehicleRequestDto vehicleRequestDto) {
        Vehicle vehicleEntity = vehicleRequestDto.toEntity();

        try {
            final String vehicleAsString = mapper.writeValueAsString(vehicleEntity);
            log.debug("vehicleAsString = {}", vehicleAsString);

            IndexRequest request = new IndexRequest(IndicesHelper.VEHICLE_INDEX_NAME);
            request.id(vehicleEntity.getId());
            request.source(vehicleAsString, XContentType.JSON); // set document source to index

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.CREATED);
        } catch(Exception e) {
            log.error("failed to save Vehicle Document to index", e);
            return false;
        }
    }

    @Override
    public Vehicle findById(final String vehicleId) {
        log.debug("vehicleId = {}", vehicleId);
        try {
            final GetResponse response = client.get(new GetRequest(IndicesHelper.VEHICLE_INDEX_NAME, vehicleId), RequestOptions.DEFAULT);
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

    @Override
    public List<Vehicle> searchContentsByMatchQuery(final SearchRequestDto searchRequestDto) {
        final SearchRequest request
                = SearchUtil.buildSearchRequest(IndicesHelper.VEHICLE_INDEX_NAME, searchRequestDto);
        log.info("request = {}", request.toString());
        return searchInternal(request);
    }

    @Override
    public List<Vehicle> searchContentsByRangeQuery(final Date date) {
        final SearchRequest request
                = SearchUtil.buildSearchRequest(IndicesHelper.VEHICLE_INDEX_NAME, "created_at", date);
        log.info("request = {}", request.toString());
        return searchInternal(request);
    }

    @Override
    public List<Vehicle> searchContentsByBoolQuery(final SearchRequestDto searchRequestDto, final Date date) {
        final SearchRequest request
                = SearchUtil.buildSearchRequest(IndicesHelper.VEHICLE_INDEX_NAME, searchRequestDto, date);
        return searchInternal(request);
    }

    private List<Vehicle> searchInternal(final SearchRequest request) {
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
