package com.example.demo.controller;

import com.example.demo.entities.Gender;
import com.example.demo.entities.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    private Person firstPerson;
    private Person secondPerson;

    @BeforeEach
    void setUp() {
        firstPerson = Person.builder().name("Maria").age(23).surname("Lopez").id(1L).gender(Gender.FEMALE).build();
        secondPerson = Person.builder().name("Maria").age(23).surname("Kriel").id(2L).gender(Gender.FEMALE).build();
    }

    @Test
    void getPersons() throws Exception {
        when(personRepository.findAll()).thenReturn(Arrays.asList(firstPerson, secondPerson));

        mockMvc.perform(get("/api/v1/persons"))
                .andExpect(status().isOk())
                // .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void getPersonById() throws Exception {
        when(personRepository.findById(1L)).thenReturn(ofNullable(firstPerson));

        mockMvc.perform(get("/api/v1/person/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name",is("Maria")))
                .andExpect(jsonPath("$.surname",is("Lopez")))
                .andExpect(jsonPath("$.age",is(23)))
                .andExpect(jsonPath("$.gender",is("FEMALE")));
    }

    @Test
    void createPerson() throws Exception {
        Person alice = Person.builder().name("Alice").surname("Garcia").age(40).gender(Gender.FEMALE).build();
        when(personRepository.save(alice)).thenReturn(alice);

        mockMvc.perform(post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alice)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name",is("Alice")));

    }

    @Test
    void updatePerson() throws Exception {
        when(personRepository.findById(1L)).thenReturn(ofNullable(firstPerson));
        when(personRepository.save(any(Person.class))).thenReturn(secondPerson);

        mockMvc.perform(put("/api/v1/person/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(secondPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname",is("Kriel")));

    }

    @Test
    void deletePerson() throws Exception {
        when(personRepository.findById(1L)).thenReturn(ofNullable(firstPerson));

        mockMvc.perform(delete("/api/v1/person/{id}",1L))
                .andExpect(status().isOk());

        verify(personRepository,times(1)).delete(firstPerson);
    }
}