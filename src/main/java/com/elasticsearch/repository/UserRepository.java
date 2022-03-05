package com.elasticsearch.repository;

import com.elasticsearch.document.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, String> {

}
