package com.elasticsearch.controller;

import com.elasticsearch.document.Person;
import com.elasticsearch.service.impl.PersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable String id) {
        log.info("id = {}", id);
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody final Person person) {
        log.info("person = {}", person);
        personService.save(person);
    }
}
