package com.elasticsearch.service;

public interface IndexService {

    void recreateIndices(boolean deleteExisting);

}
