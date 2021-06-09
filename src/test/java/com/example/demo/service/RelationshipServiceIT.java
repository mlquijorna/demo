package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RelationshipServiceIT {
//
//    private Person father;
//    private Person firstChild;
//    private Person secondChild;
//
//    @Autowired
//    RelationshipService relationshipService;
//
//    @Autowired
//    PersonRespository personRespository;
//
//    @Autowired
//    PopulateService populateService;
//
//    @BeforeEach
//    void setUp() {
//        relationshipService = new RelationshipService(personRespository);
//        populateService = new PopulateService(personRespository);
//        father = populateService.generatePersons(1, 95, 105).get(0);
//        populateService.generateChildrenFor()
//    }
//}
