package com.elasticsearch.service.impl;

import com.elasticsearch.document.User;
import com.elasticsearch.document.Vehicle;
import com.elasticsearch.dto.request.UserRequestDto;
import com.elasticsearch.dto.request.VehicleRequestDto;
import com.elasticsearch.helper.IndexMappingUtils;
import com.elasticsearch.helper.IndicesHelper;
import com.elasticsearch.service.DummyDataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 더미 데이터 색인(Indexing)
 *
 * @author ymkim
 * @since 2022.03.05 Sat 23:26
 * @desc 테스트 용도로 사용하는 경우에는 해당 API 호출하여 데이터 등록 -> DummyDataController
 */
@Service
@RequiredArgsConstructor
public class DummyDataServiceImpl implements DummyDataService {
    private static final Logger log = LoggerFactory.getLogger(DummyDataServiceImpl.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final ObjectMapper mapper = new ObjectMapper();
    private final UserServiceImpl userServiceImpl;
    private final VehicleServiceImpl vehicleService;

    /**
     * script/*.json 파일을 읽어들여 데이터를 Elasticsearch에 인덱싱 하는 메소드 입니다.
     *
     * @param indexName : [String] 데이터 등록을 원하는 인덱스명
     */
    @Override
    public Boolean insertDummyData(String indexName) {
        if (StringUtils.isBlank(indexName)) {
            return false;
        }

        boolean isSuccess = false;
        try {
            String jsonPath = getJsonFilePath(indexName);
            log.info("===================================================================");
            log.info("Start ==> INSERT DUMMY DATA");
            log.info("jsonPath = {}", jsonPath);
            log.info("===================================================================");
            File file = new File(jsonPath);

            if (indexName.equalsIgnoreCase(IndicesHelper.USER_INDEX_NAME)) {
                List<UserRequestDto> userList = mapper.readValue(file, new TypeReference<>() {});
                log.info("userList = {}", userList.toString());

                for (UserRequestDto user : userList) {
                    userServiceImpl.saveDocumentToIndex(user);
                }
            } else if (indexName.equalsIgnoreCase(IndicesHelper.VEHICLE_INDEX_NAME)) {
                List<VehicleRequestDto> vehicleList = mapper.readValue(file, new TypeReference<>() {});
                log.info("vehicleList = {}", vehicleList.toString());

                for (VehicleRequestDto vehicle : vehicleList) {
                    vehicleService.saveDocumentToIndex(vehicle);
                }
            }
        } catch(Exception e) {
            log.error("failed to insert dummy data", e);
        }
        return isSuccess;
    }

    private String getJsonFilePath(String indexName) throws IOException {
        return new ClassPathResource("/static/script/" + indexName + ".json").getFile().getAbsolutePath();
    }
}
