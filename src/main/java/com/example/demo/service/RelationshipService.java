package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class RelationshipService {
//
//    private PersonRespository personRespository;
//
//
//    @Autowired
//    public RelationshipService(PersonRespository personRespository) {
//        this.personRespository = personRespository;
//    }
//
//    public int getDegreeRelationShip(Person firstPerson, Person secondPerson) {
//        long parent_id = firstPerson.getParent_id();
//        if (personRespository.findAllSiblings(parent_id).contains(secondPerson)){
//            return 0;
//        }
//        return -1;
//    }
//}
