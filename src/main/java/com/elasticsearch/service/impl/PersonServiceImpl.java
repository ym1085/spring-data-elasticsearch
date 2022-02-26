package com.elasticsearch.service.impl;

import com.elasticsearch.document.Person;
import com.elasticsearch.repository.PersonRepository;
import com.elasticsearch.service.PersonService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    @Override
    public Person findById(final String id) {
        return personRepository
                .findById(id).orElseThrow(() -> new IllegalStateException());
    }

    @Override
    public void save(final Person person) {
        personRepository.save(person);
    }
}
