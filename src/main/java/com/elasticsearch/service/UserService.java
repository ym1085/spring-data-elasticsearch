package com.elasticsearch.service;

import com.elasticsearch.document.User;
import com.elasticsearch.dto.UserRequest;
import com.elasticsearch.helper.IndicesHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient client;

    public Boolean saveDocumentToIndex(UserRequest.request userRequestDto) {
        User userEntity = userRequestDto.toEntity();
        try {
            String userJsonObj = mapper.writeValueAsString(userEntity);

            IndexRequest request = new IndexRequest(IndicesHelper.USER_INDEX_NAME);
            request.id(userEntity.getId());
            request.source(userJsonObj, XContentType.JSON);

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.CREATED);
        } catch (Exception e) {
            log.error("failed to save User Document to index", e);
            return false;
        }
    }

    public User findById(UserRequest.request request) {
        User user = null;
        try {
            user = getUserContentsFromDocument(request.toEntity());
        } catch (IOException e) {
            log.error("failed to get document", e);
        }
        return user;
    }

    private User getUserContentsFromDocument(User user) throws IOException {
        GetResponse response = client.get(new GetRequest(IndicesHelper.USER_INDEX_NAME, user.getId()), RequestOptions.DEFAULT);
        if (response.isSourceEmpty()) {
            return new User();
        }
        log.trace("response = {}", response.toString());
        return mapper.readValue(response.getSourceAsString(), User.class);
    }
}
