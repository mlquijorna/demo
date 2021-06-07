package com.example.demo.controller;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonRespository personRespository;

    @Autowired
    public PersonController(PersonRespository personRespository) {
        this.personRespository = personRespository;
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons() {
        return ResponseEntity.ok().body(personRespository.findAll());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId) {
        Person person = personRespository.findById(personId)
                .orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        uriComponentsBuilder.path("/api/v1/person").buildAndExpand(personRespository.save(person));

        return ResponseEntity.created(URI.create(uriComponentsBuilder.toUriString())).body(person);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId, @Valid @RequestBody Person person) {
        personRespository
                .findById(personId)
                .orElseThrow(NoSuchElementException::new);

        person.setAge(person.getAge());
        person.setGender(person.getGender());
        person.setSurname(person.getSurname());
        person.setName(person.getName());

        Person updatedPerson = personRespository.save(person);

        return ResponseEntity.ok().body(updatedPerson);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable(value = "id") Long personId) {
        Person personToBeDeleted = personRespository
                .findById(personId)
                .orElseThrow(NoSuchElementException::new);

        personRespository.delete(personToBeDeleted);
        return ResponseEntity.ok().body(personToBeDeleted);
    }
}
