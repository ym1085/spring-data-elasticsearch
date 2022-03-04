package com.elasticsearch.service.impl;

import com.elasticsearch.document.Vehicle;
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
     * Document를 지정한 인덱스에 생성하며, Document는 String 형태로 기술한다.
     *
     * @desc
     *      IndexRequest 클래스는 문서 생성을 담당하며,
     *      해당 클래스의 생성자는 인덱스명, 타입명, 문서Id를 받는다.
     */
    @Override
    public Boolean createDocumentToIndex(final Vehicle vehicle) {
        try {
            final String vehicleAsString = mapper.writeValueAsString(vehicle);
            log.debug("vehicleAsString = {}", vehicleAsString);

            IndexRequest request = new IndexRequest(IndicesHelper.VEHICLE_IDX);
            request.id(vehicle.getId()); // Sets the id of the indexed document. If not set, will be automatically generated.
            request.source(vehicleAsString, XContentType.JSON); // Sets the document source to index

            IndexResponse response = client.index(request, RequestOptions.DEFAULT); // indexing
            return response != null && response.status().equals(RestStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 차량 번호 기반 조회
     *
     * @param vehicleId
     */
    @Override
    public Vehicle findById(final String vehicleId) {
        log.debug("vehicleId = {}", vehicleId);
        try {
            final GetResponse response = client.get(new GetRequest(IndicesHelper.VEHICLE_IDX, vehicleId), RequestOptions.DEFAULT);
            log.debug("response = {}", response.toString());

            if (response == null || response.isSourceEmpty()) {
                return new Vehicle();
            }
            return mapper.readValue(response.getSourceAsString(), Vehicle.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * match query 기반 검색
     *
     * @param searchRequestDto
     */
    @Override
    public List<Vehicle> searchByMatchOrMultiMatchQuery(final SearchRequestDto searchRequestDto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
            IndicesHelper.VEHICLE_IDX,
            searchRequestDto
        );

        log.info("request = {}", request.toString());
        return searchInternal(request);
    }

    /**
     * 날짜 기반 검색
     *
     * @param date
     */
    @Override
    public List<Vehicle> searchByDate(final Date date) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
            IndicesHelper.VEHICLE_IDX,
            "created_at",
            date
        );

        log.info("request = {}", request.toString());
        return searchInternal(request);
    }

    @Override
    public List<Vehicle> searchByContentsAndDate(final SearchRequestDto searchRequestDto, final Date date) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
            IndicesHelper.VEHICLE_IDX,
            searchRequestDto,
            date
        );
        return searchInternal(request);
    }

    private List<Vehicle> searchInternal(final SearchRequest request) {
        if (request == null) {
            log.error("Failed to build search request query");
            return Collections.emptyList();
        }

        List<Vehicle> vehicle = null;
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT); // execute searching(query, option)
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
        log.info("vehicle = {}", vehicle.toString());
        return vehicle;
    }
}
