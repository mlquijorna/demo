//package com.example.demo.service;
//
//import com.example.demo.entities.Gender;
//import com.example.demo.entities.Person;
//import com.example.demo.repository.PersonRespository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RelationshipServiceTest {
//
//
//    private Person father;
//    private Person firstChild;
//    private Person secondChild;
//
//    @Autowired
//    RelationshipService relationshipService;
//
//    @Mock
//    PersonRespository personRespository;
//
//    @BeforeEach
//    void setUp() {
//        relationshipService = new RelationshipService(personRespository);
//        Person father = createPerson();
//        Person firstChild = createChildOf(father);
//        Person secondChild = createChildOf(father);
//        father.add(Arrays.asList(firstChild, secondChild));
//    }
//
//    @Test
//    void getDegreeRelationShip_shouldReturn0_IfPersonsAreSiblings() {
//
//
//    }
//
//    private Person createChildOf(Person father) {
//        return Person.builder()
//                .name("firstChild")
//                .surname(father.getSurname())
//                .age(1)
//                .gender(Gender.FEMALE)
//                .parent_id(father.getId())
//                .build();
//    }
//
//    private Person createPerson() {
//        return Person.builder()
//                .name("fatherName")
//                .surname("fatherSurname")
//                .age(40)
//                .gender(Gender.MALE)
//                .build();
//    }
//}