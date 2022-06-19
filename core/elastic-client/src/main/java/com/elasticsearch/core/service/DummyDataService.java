package com.elasticsearch.core.service;

import com.elasticsearch.core.search.dto.req.UserRequestDto;
import com.elasticsearch.core.search.dto.req.VehicleRequestDto;
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

@Slf4j
@RequiredArgsConstructor
@Service
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
                    userService.saveUserInfo(user);
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
