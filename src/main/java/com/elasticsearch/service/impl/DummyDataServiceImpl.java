package com.elasticsearch.service.impl;

import com.elasticsearch.document.Vehicle;
import com.elasticsearch.service.DummyDataService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class DummyDataServiceImpl implements DummyDataService {
    private static final Logger log = LoggerFactory.getLogger(DummyDataServiceImpl.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final VehicleServiceImpl vehicleService;

    @Override
    public void insertDummyDataToIndex() {
        vehicleService.saveDocumentToIndex(buildVehicle("1", "Audi A1", "AAA-123", "2010-01-01"));
        vehicleService.saveDocumentToIndex(buildVehicle("2", "Audi A3", "AAB-123", "2011-07-05"));
        vehicleService.saveDocumentToIndex(buildVehicle("3", "Audi A3", "AAC-123", "2012-10-03"));
        vehicleService.saveDocumentToIndex(buildVehicle("4", "BMW M3", "AAA-023", "2021-10-06"));
        vehicleService.saveDocumentToIndex(buildVehicle("5", "BMW 3", "1AA-023", "2001-10-01"));
        vehicleService.saveDocumentToIndex(buildVehicle("6", "BMW M5", "12A-023", "1999-05-08"));
        vehicleService.saveDocumentToIndex(buildVehicle("7", "VW Golf", "42A-023", "1991-04-08"));
        vehicleService.saveDocumentToIndex(buildVehicle("8", "VW Passat", "18A-023", "2021-04-08"));
        vehicleService.saveDocumentToIndex(buildVehicle("9", "Skoda Kodiaq", "28A-023", "2020-01-04"));
        vehicleService.saveDocumentToIndex(buildVehicle("10", "Skoda Yeti", "88A-023", "2015-03-09"));
    }

    public static Vehicle buildVehicle(String id, String name, String vehicleNumber, String date) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setVehicleName(name);
        vehicle.setVehicleNumber(vehicleNumber);
        try {
            vehicle.setCreatedAt(DATE_FORMAT.parse(date));
            vehicle.setUpdatedAt(DATE_FORMAT.parse(date));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return vehicle;
    }
}
