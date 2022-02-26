package com.elasticsearch.service;

import com.elasticsearch.document.Vehicle;

public interface VehicleService {

    Vehicle getById(String id);

    Boolean createDocumentToIndex(Vehicle vehicle);

}
