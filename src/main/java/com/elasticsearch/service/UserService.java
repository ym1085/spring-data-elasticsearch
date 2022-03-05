package com.elasticsearch.service;

import com.elasticsearch.document.User;

public interface UserService {

    User findById(String id);

    void save(User user);

}
