package com.example.demo.repository;

import com.example.demo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRespository extends JpaRepository<Person,Long> {
//
//    @Query("SELECT * FROM Person p WHERE p.parent_id = :parent_id")
//    List<Person> findAllSiblings(@Param("parent_id") long parent_id);

}
