package com.example.demo.repository;

import com.example.demo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Query("select p.id from Person p WHERE p.parent_id = :parent_id")
    List<Long> findAllSiblings(@Param("parent_id") Long parent_id);

}
