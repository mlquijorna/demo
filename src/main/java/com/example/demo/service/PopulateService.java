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

        // TODO: maria 08/06/2021  think better name as i
        for (int i = 0; i < numberOfPersons; i++) {
            Person person = Person.builder()
                    .name("name" + i)
                    .gender(Gender.NONE)
                    .surname("surname" + i)
                    .age(getRandomNumberUsingNextInt(minAge, maxAge))
                    .build();
            randomPersons.add(person);
        }
        return personRespository.saveAll(randomPersons);
    }

    private List<Person> generatechildrenFor(List<Person> parents) {
        List<Person> totalChildren = new ArrayList<>();
        for (Person person : parents) {
            List<Person> children = generatechildrenFor(person);
            totalChildren.addAll(children);
        }
        return totalChildren;
    }

    private List<Person> generatechildrenFor(Person person) {
        int numberOfChildren = getRandomNumberUsingNextInt(1, 5);
        int parentAge = person.getAge();
        List<Person> childs = generatePersons(numberOfChildren, parentAge - 5, parentAge + 5);
        person.add(childs);
        return childs;
    }

    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
