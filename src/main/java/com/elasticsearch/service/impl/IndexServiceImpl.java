package com.elasticsearch.service.impl;

import com.elasticsearch.helper.IndexMappingUtils;
import com.elasticsearch.helper.IndicesHelper;
import com.elasticsearch.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {
    private final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

    private static final List<String> INDICES_TO_CREATE = List.of(IndicesHelper.VEHICLE_IDX, IndicesHelper.PERSON_IDX);
    private final RestHighLevelClient client;

    @Override
    public void recreateIndices(final Map<String, Object> paramMap) {
        final String settings = IndexMappingUtils.loadAsString("static/indices/es-settings.json");

        String deleteIndexName = "";
        boolean isRecreateIndex = false;
        if (paramMap != null) {
            isRecreateIndex = (boolean) paramMap.get("isRecreateIndex");
            deleteIndexName = (String) paramMap.get("deleteIndexName");
        }
        log.warn("isRecreateIndex = {}, deleteIndexName = {}", isRecreateIndex, deleteIndexName);

        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean isExistsIndex = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                log.debug("isExistsIndex = {}", isExistsIndex);

                // code smell..
                if (isExistsIndex) {
                    if (isRecreateIndex) { // request delete and recreate index
                        if (StringUtils.isNotBlank(deleteIndexName) && indexName.equalsIgnoreCase(deleteIndexName)) {
                            log.info("Delete = {}", deleteIndexName);
                            client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
                        }
                    } else {
                        continue;
                    }
                }
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName); // 인덱스 생성을 위한 객체 생성
                createIndexRequest.settings(settings, XContentType.JSON);

//                TODO: 2022-03-05 01:01분 => type=resource_already_exists_exception, 공백 들어오면 그냥 애초에 return 해버리면 될 것 같음?
                final String mappings = getIndicesMappingInfo(indexName);
                if (StringUtils.isNotBlank(mappings)) {
                    createIndexRequest.mapping(mappings, XContentType.JSON);
                    client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                } else {
                    log.warn(">>>>>>>> mappings info is NULL!");
                }
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * static > indices > mappings > *.json 파일 형식을 읽어 반환 합니다.
     *
     * @param indexName
     * @return null || mappings
     */
    private String getIndicesMappingInfo(String indexName) {
        String mappings = "";
        try {
            mappings = IndexMappingUtils.loadAsString("static/indices/mappings/" + indexName + ".json");
            if (StringUtils.isBlank(mappings)) {
                return null;
            }
        } catch (final Exception e) {
            log.error("Failed to load mappings for index with name = {}", indexName);
            return null;
        }
        return mappings;
    }
}
