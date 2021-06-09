package com.example.demo.service;

import com.example.demo.entities.Gender;
import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PopulateService {

    private PersonRespository personRespository;

    @Autowired
    public PopulateService(PersonRespository personRespository) {
        this.personRespository = personRespository;
    }

    public List<Person> generatePersons(int numberOfPersons, int minAge, int maxAge) {
        List<Person> randomPersons = new ArrayList<>();

        // TODO: maria 08/06/2021  think better name as index
        for (int index = 0; index < numberOfPersons; index++) {
            Person person = createPersonWith(minAge, maxAge, index);
            randomPersons.add(person);
        }
        return personRespository.saveAll(randomPersons);
    }

    public List<Person> generateChildrenFor(List<Person> parents) {
        List<Person> totalChildren = new ArrayList<>();
        for (Person parent : parents) {
            List<Person> children = generateChildrenFor(parent);
            totalChildren.addAll(children);
        }
        return personRespository.saveAll(totalChildren);
    }

    private List<Person> generateChildrenFor(Person parent) {
        int numberOfPersons = getRandomNumberUsingNextInt(1, 5);
        List<Person> childrenToSave = generateChildrenFor(parent, numberOfPersons);
        List<Person> children = personRespository.saveAll(childrenToSave);
        parent.add(children);
        personRespository.saveAndFlush(parent);
        return children;
    }

    private List<Person> generateChildrenFor(int numberOfChildren, Person person) {
        List<Person> childrenToSave = generateChildrenFor(person, numberOfChildren);
        List<Person> children = personRespository.saveAll(childrenToSave);
        person.add(children);
        personRespository.saveAndFlush(person);
        return children;
    }

    private List<Person> generateChildrenFor(Person parent, int numberOfPersons) {
        List<Person> children = new ArrayList<>();
        for (int indexName = 0; indexName < numberOfPersons; indexName++) {
            Person child = createPersonWith(parent, indexName);
            children.add(child);
        }
        return children;
    }

    private Person createPersonWith(Person parent, int indexName) {
        Person person = createPersonWith(parent.getAge() - 35, parent.getAge() + 35, indexName);
        person.setSurname(parent.getSurname());
        return person;
    }

    private Person createPersonWith(int minAge, int maxAge, int index) {
        return Person.builder()
                .name("name" + index)
                .gender(Gender.NONE)
                .surname("surname" + index)
                .age(getRandomNumberUsingNextInt(minAge, maxAge))
                .build();
    }

    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
