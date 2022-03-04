package com.elasticsearch.service;

import java.util.Map;

public interface IndexService {

    void recreateIndices(Map<String, Object> paramMap);

}
