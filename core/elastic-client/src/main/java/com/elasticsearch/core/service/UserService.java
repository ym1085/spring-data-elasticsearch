package com.elasticsearch.core.service;

import com.elasticsearch.core.document.User;
import com.elasticsearch.core.search.dto.req.UserRequestDto;
import com.elasticsearch.core.helper.IndicesHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient client;

    public User retrieveByUserId(String id) {
        User user = null;
        try {
            user = retrieveUserContentByUserId(id);
        } catch (IOException e) {
            log.error("fail to retrieve user contents by id", e);
        }
        return user;
    }

    public boolean saveUserInfo(UserRequestDto.request userRequestDto) {
        boolean response = false;
        try {
            response = saveUserInfoToIndex(userRequestDto);
        } catch (Exception e) {
            log.error("failed to save User Document to index", e);
        }
        return response;
    }

    private boolean saveUserInfoToIndex(UserRequestDto.request userRequestDto) throws IOException {
        User user = userRequestDto.toEntity();
        String userObj = mapper.writeValueAsString(user);

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("user_id", userRequestDto.getUserId()));
        SearchResponse searchResponse = client.search(new SearchRequest(IndicesHelper.USER_INDEX_NAME).source(query), RequestOptions.DEFAULT);
//        log.info("searchResponse.size = {}", searchResponse.getHits().getTotalHits().value);

        // 기존 회원 존재 여부 검토 로직
        if (searchResponse.getHits().getTotalHits().value > 0) {
            for (SearchHit hits : searchResponse.getHits()) {
                if (StringUtils.isNotBlank(hits.getSourceAsString()) || hits.getSourceAsString().length() > 0) {
                    log.info("user information exists");
                    return false;
                }
            }
        }

        IndexRequest request = new IndexRequest(IndicesHelper.USER_INDEX_NAME)
                .id(user.getId())
                .source(userObj, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.status().equals(RestStatus.CREATED);
    }

    private User retrieveUserContentByUserId(String id) throws IOException {
        GetRequest request = new GetRequest(IndicesHelper.USER_INDEX_NAME, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if (!response.isExists()) {
            return new User();
        }
        log.debug("response = {}", response.toString());
        return mapper.readValue(response.getSourceAsString(), User.class);
    }
}
