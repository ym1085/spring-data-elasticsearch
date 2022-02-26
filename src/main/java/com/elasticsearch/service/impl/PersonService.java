package com.elasticsearch.service.impl;

import com.elasticsearch.document.Person;

import java.util.List;

public interface PersonService {

    Person findById(String id);

    void save(Person person);

}
