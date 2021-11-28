package com.dravaib.dravaib;

import com.dravaib.dravaib.factory.PlaceFactory;
import com.dravaib.dravaib.factory.UserFactory;
import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.repository.PlaceRepository;
import com.dravaib.dravaib.repository.UserRepository;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = DravaibApplication.class)
// @AutoConfigureMockMvc(addFilters = false)
// @EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
// @TestPropertySource(locations = "classpath:application.test.properties")
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PlaceControllerTests {
    // @Autowired
    // private PlaceRepository repository;

    // @Autowired
    // private UserRepository userRepository;

    // @Autowired
    // private PlaceFactory placeFactory;

    // @Autowired
    // private UserFactory userFactory;

    // @Autowired
    // private MockMvc mvc;

    // @After
    // public void tearDown() {
    // repository.deleteAll();
    // }

    // @Test
    // public void test1() throws Exception {

    // var owner = userRepository.save(userFactory.make(RoleType.ROLE_OWNER));
    // Place place = placeFactory.make(owner);

    // repository.save(place);

    // mvc.perform(get("/api/place")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$",
    // hasSize(1)))
    // .andExpect(jsonPath("$[0].name", is(place.getName()))).andReturn();
    // }

}
