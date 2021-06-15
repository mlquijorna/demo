package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationshipServiceIT {

    private Person father;
    private Person firstChild;
    private Person secondChild;

    @Autowired
    RelationshipService relationshipService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PopulateService populateService;

    @Before
    public void setUp() {
        relationshipService = new RelationshipService(personRepository);
        populateService = new PopulateService(personRepository);
        father = populateService.generatePersons(1, 95, 105).get(0);
        List<Person> children = populateService.generateChildrenFor(father, 2);
        firstChild = children.get(0);
        secondChild = children.get(1);
    }

    @Test
    public void getDegreeRelationShip_shouldReturn0IfPersonsAreSiblings() {
        int degreeRelationShip = relationshipService.getDegreeRelationShip(firstChild, secondChild);

        assertThat(degreeRelationShip,equalTo(0));
    }

    @Test
    public void getDegreeRelationShip_ShouldReturn1IfPersonsAreParentAndChild() {
        int degreeRelationShip = relationshipService.getDegreeRelationShip(father, firstChild);

        assertThat(degreeRelationShip, equalTo(1));
    }

    @Test
    public void getDegreeRelationShip_ShouldReturn2IfPersonsAreGranFatherAndGrandChild() {
        Person grandChild = populateService.generateChildrenFor(firstChild, 1).get(0);

        int degreeRelationShip = relationshipService.getDegreeRelationShip(father, grandChild);

        assertThat(degreeRelationShip, equalTo(2));
    }

    @Test
    public void collectGroupOfSiblings_shouldReturnAllGroupsOfSiblings_whenAgeIsLessOrEqualToTen() {
        List<Person> firstGeneration = populateService.generatePersons(50, 95, 105);
        List<Person> secondGeneration = populateService.generateChildrenFor(firstGeneration);
        List<Person> thirdGeneration = populateService.generateChildrenFor(secondGeneration);
        List<Person> allPopulation = new ArrayList<>();
        Stream.of(firstGeneration, secondGeneration, thirdGeneration).forEach(allPopulation::addAll);;
        List<Person> personWithAgeLessOrEqualToTen = allPopulation.stream().filter(p -> p.getAge() <= 10).collect(Collectors.toList());
        System.out.println(personWithAgeLessOrEqualToTen.size());

        Set<List<Person>> groupOfSiblings = relationshipService.collectGroupOfSiblings(personWithAgeLessOrEqualToTen);


        assertThat(groupOfSiblings.size(), is(0));
    }

    @Test
    public void collectGroupOfSiblings_shouldReturnAllGroupsOfSiblings_whenAgeisLessOrEqualto35() {
        List<Person> firstGeneration = populateService.generatePersons(50, 95, 105);
        System.out.println("These are the persons of the first generation " + firstGeneration.size() + firstGeneration);

        List<Person> secondGeneration = populateService.generateChildrenFor(firstGeneration);
        System.out.println("These are the persons of the second generation " + secondGeneration.size() + secondGeneration);

        List<Person> thirdGeneration = populateService.generateChildrenFor(secondGeneration);
        System.out.println("These are the persons of the third generation " + thirdGeneration.size() + thirdGeneration);

        List<Person> allPopulation = new ArrayList<>();
        Stream.of(firstGeneration, secondGeneration, thirdGeneration).forEach(allPopulation::addAll);;
        List<Person> personWithAgeLessOrEqualTo35 = allPopulation.stream().filter(p -> p.getAge() <= 30).collect(Collectors.toList());

        Set<List<Person>> groupOfSiblings = relationshipService.collectGroupOfSiblings(personWithAgeLessOrEqualTo35);
        System.out.println("These are the person with age less than 35: " + personWithAgeLessOrEqualTo35.size() +personWithAgeLessOrEqualTo35);
        System.out.println("These are the siblings: " + groupOfSiblings.size()+ groupOfSiblings);


        assertThat(groupOfSiblings.size(), greaterThanOrEqualTo(3));
    }
}
