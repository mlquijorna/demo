package com.example.demo.entities;

public enum Gender {
    FEMALE("female"),
    MALE("male"),
    NONE("none");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
