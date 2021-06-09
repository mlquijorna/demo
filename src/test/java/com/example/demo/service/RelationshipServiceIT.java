package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
}
