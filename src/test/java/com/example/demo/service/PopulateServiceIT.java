package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRespository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PopulateServiceIT {
    @Autowired
    private PersonRespository personRepository;

    @Autowired
    private PopulateService populateService;

    @Before
    public void setUp() {
        populateService = new PopulateService(personRepository);
    }

    // TODO: maria 08/06/2021  Try maybe with less numberOfperson because of performance reasons
    @Test
    public void generatePersons_shouldGeneratePerson_whichAgeIsGreaterThanOrEqualTo_givenMinAge() {
        int minAge = 95;
        int maxAge = 105;

        List<Person> persons = populateService.generatePersons(10, minAge, maxAge);

        assertThat(persons, everyItem(hasProperty("age", greaterThanOrEqualTo(minAge))));
    }

    @Test
    public void generatePersons_shouldGeneratePerson_whichAgeIsLessThanOrEqualTo_givenMaxAge() {
        int minAge = 95;
        int maxAge = 105;

        List<Person> persons = populateService.generatePersons(10, minAge, maxAge);

        assertThat(persons, everyItem(hasProperty("age", lessThanOrEqualTo(maxAge))));
    }
}