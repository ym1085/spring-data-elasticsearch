package com.elasticsearch.service.impl;

import com.elasticsearch.helper.IndexMappingUtils;
import com.elasticsearch.helper.IndicesHelper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl {
    private final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

    private final List<String> INDICES_TO_CREATE = List.of(IndicesHelper.VEHICLE_IDX);
    private final RestHighLevelClient client;

    @PostConstruct
    public void createIndices() {
        final String path = "static/indices/mappings/";
        final String ext = ".json";
        final String settings = IndexMappingUtils.loadAsString("static/indices/es-settings.json");

//        Create Index
        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean isIndexExists = client
                        .indices()
                        .exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (isIndexExists) {
                    continue;
                }

                final String mappings = IndexMappingUtils.loadAsString(path + indexName + ext);
                if (settings == null || mappings == null) {
                    log.error("Failed to create index with name = {}", indexName);
                    continue;
                }

                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mappings, XContentType.JSON);

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
