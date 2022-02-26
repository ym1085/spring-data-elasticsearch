package com.elasticsearch.service;

import com.elasticsearch.document.Person;
import com.elasticsearch.repository.PersonRepository;
import com.elasticsearch.service.impl.PersonService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    final Logger log = LoggerFactory.getLogger(this.getClass());

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
