package com.elasticsearch.service.impl;

import com.elasticsearch.document.User;
import com.elasticsearch.dto.request.UserRequestDto;
import com.elasticsearch.helper.IndicesHelper;
import com.elasticsearch.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestHighLevelClient client;

    @Override
    public Boolean saveDocumentToIndex(final UserRequestDto userRequestDto) {
        User userEntity = userRequestDto.toEntity();
        log.debug("userEntity.getId = {}", userEntity.getId());

        try {
            final String userJsonObj = mapper.writeValueAsString(userEntity);

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

    @Override
    public User findById(final UserRequestDto userRequestDto) {
        User userEntity = userRequestDto.toEntity();
        log.debug("userEntity = {}", userEntity.toString());

        User user = null;
        try {
            user = getUserContentsFromDocument(userEntity);
        } catch (IOException e) {
            log.error("failed to get document", e);
        }
        return user;
    }

    private User getUserContentsFromDocument(final User user) throws IOException {
        final GetResponse response
                = client.get(new GetRequest(IndicesHelper.USER_INDEX_NAME, user.getId()), RequestOptions.DEFAULT);
        if (response.isSourceEmpty()) {
            return new User();
        }
        log.trace("response = {}", response.toString());
        return mapper.readValue(response.getSourceAsString(), User.class);
    }
}
