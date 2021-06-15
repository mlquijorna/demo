package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
@EntityListeners(AuditingEntityListener.class)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private Gender gender;

    //@JoinColumn(name = "id", nullable = true, insertable = false, updatable = false, columnDefinition = "long default null")
    //@ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private long parent_id;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Person> children;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public long getParent_id() {
        return parent_id;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void add(List<Person> children) {
        this.children.addAll(children);
    }
}
