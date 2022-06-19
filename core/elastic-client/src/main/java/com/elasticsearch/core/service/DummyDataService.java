package com.elasticsearch.core.service;

import com.elasticsearch.core.dto.UserRequestDto;
import com.elasticsearch.core.dto.VehicleRequestDto;
import com.elasticsearch.core.helper.IndicesHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class DummyDataService {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final ObjectMapper mapper = new ObjectMapper();
    private final UserService userService;
    private final VehicleService vehicleService;

    public Boolean insertDummyData(String indexName) {
        if (StringUtils.isBlank(indexName)) {
            return false;
        }
        boolean isSuccess = false;
        try {
            String jsonPath = getJsonFilePath(indexName);
            File file = new File(jsonPath);
            if (indexName.equalsIgnoreCase(IndicesHelper.USER_INDEX_NAME)) {
                List<UserRequestDto.request> users = mapper.readValue(file, new TypeReference<List<UserRequestDto.request>>() {});
                for (UserRequestDto.request user : users) {
                    userService.saveDocumentToIndex(user);
                }
            } else if (indexName.equalsIgnoreCase(IndicesHelper.VEHICLE_INDEX_NAME)) {
                List<VehicleRequestDto.request> vehicles = mapper.readValue(file, new TypeReference<List<VehicleRequestDto.request>>() {});
                for (VehicleRequestDto.request vehicle : vehicles) {
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
