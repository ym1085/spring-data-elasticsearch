package com.elasticsearch.core.service;

import com.elasticsearch.core.helper.IndexMappingUtils;
import com.elasticsearch.core.helper.IndicesHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class IndexService {

    private final List<String> INDICES_TO_CREATE = new ArrayList<>(Arrays.asList(IndicesHelper.VEHICLE_INDEX_NAME, IndicesHelper.USER_INDEX_NAME));
    private final RestHighLevelClient client;

    public void recreateIndices(Map<String, Object> paramMap) {
        String settings = IndexMappingUtils.loadAsString("static/indices/es-settings.json");

        String deleteIndexName = "";
        boolean isRecreateIndexYn = false;
        if (paramMap != null) {
            isRecreateIndexYn = (boolean) paramMap.get("isRecreateIndexYn");
            deleteIndexName = (String) paramMap.get("deleteIndexName");
        }
        log.warn("isRecreateIndexYn = {}, deleteIndexName = {}", isRecreateIndexYn, deleteIndexName);

        for (String indexName : INDICES_TO_CREATE) {
            try {
                boolean isExistsIndex = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                log.debug("isExistsIndex = {}", isExistsIndex);

                // FIXME: 중첩 IF문 다른 로직으로 풀 수 있는 방안 고민
                if (isExistsIndex) {
                    if (isRecreateIndexYn) { // request delete and recreate index
                        if (StringUtils.isNotBlank(deleteIndexName) && indexName.equalsIgnoreCase(deleteIndexName)) {
                            log.info("Delete = {}", deleteIndexName);
                            client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
                        }
                    } else {
                        continue;
                    }
                }
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName); // 인덱스 생성을 위한 객체 생성
                createIndexRequest.settings(settings, XContentType.JSON);

                String mappings = getIndicesMappingInfo(indexName);
                if (StringUtils.isNotBlank(mappings)) {
                    createIndexRequest.mapping(mappings, XContentType.JSON);
                    client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                } else {
                    log.warn("mappings info is NULL");
                }
            } catch (Exception e) {
                log.error("failed to recreate index", e);
            }
        }
    }

    private String getIndicesMappingInfo(String indexName) {
        String mappings = IndexMappingUtils.loadAsString("static/indices/mappings/" + indexName + ".json");
        if (StringUtils.isBlank(mappings)) {
            return null;
        }
        return mappings;
    }
}
