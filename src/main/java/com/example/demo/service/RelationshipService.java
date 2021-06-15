package com.example.demo.service;

import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelationshipService {

    private PersonRepository personRepository;

    @Autowired
    public RelationshipService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public int getDegreeRelationShip(Person firstPerson, Person secondPerson) {
        if (areSiblings(firstPerson, secondPerson)) {
            return 0;
        }

        if (isParentAndChild(firstPerson, secondPerson) || isParentAndChild(secondPerson, firstPerson)) {
            return 1;
        }

        if (areGrandFatherAndChild(firstPerson, secondPerson) || areGrandFatherAndChild(secondPerson, firstPerson)) {
            return 2;
        }

        return -1;
    }

    public Set<List<Person>> collectGroupOfSiblings(List<Person> persons) {
        HashSet<List<Person>> groupOfSibling = new HashSet<>();
        Set<Person> personSet = new HashSet<>(persons);

        while (!personSet.isEmpty()) {
            for (Person person : persons) {
                ArrayList<Person> siblings = new ArrayList<>();
                persons.stream().filter(p -> areSiblings(p, person)).forEach(p-> {siblings.add(p);personSet.remove(p);});
                groupOfSibling.add(siblings);
            }
        }
        return groupOfSibling;
    }

    private boolean areGrandFatherAndChild(Person firstPerson, Person secondPerson) {
        Optional<Person> optionalFather = personRepository.findById(secondPerson.getParent_id());

        if (optionalFather.isPresent()) {
            Person fatherSecondPerson = optionalFather.get();
            long grandFatherSecondPersonId = fatherSecondPerson.getParent_id();

            return firstPerson.getId() == grandFatherSecondPersonId;
        }
        return false;
    }

    private boolean isParentAndChild(Person firstPerson, Person secondPerson) {
        return firstPerson.getId() == secondPerson.getParent_id();
    }

    private boolean areSiblings(Person firstPerson, Person secondPerson) {
        return personRepository.findAllSiblings(firstPerson.getParent_id()).contains(secondPerson.getId());
    }
}
