package com.elasticsearch.service.impl;

import com.elasticsearch.helper.IndexMappingUtils;
import com.elasticsearch.helper.IndicesHelper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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
        recreateIndices(false);
    }

    private void recreateIndices(boolean deleteExisting) {
        final String settings = IndexMappingUtils.loadAsString("static/indices/es-settings.json");

//        Create Index
        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean isIndexExists = client
                        .indices()
                        .exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (isIndexExists) {
                    if (!deleteExisting) {
                        continue;
                    }
                    client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT); // Delete index
                }

                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);

                final String mappings = loadMappings(indexName);
                if (mappings != null) {
                    createIndexRequest.mapping(mappings, XContentType.JSON);
                }

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private String loadMappings(String indexName) {
        final String mappings = IndexMappingUtils.loadAsString("static/indices/mappings/" + indexName + ".json");
        if (mappings == null) {
            log.error("Failed to load mappings for index with name = {}", indexName);
            return null;
        }
        return mappings;
    }
}
