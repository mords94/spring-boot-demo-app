package com.dravaib.dravaib;

import com.dravaib.dravaib.factory.CountryFactory;
import com.dravaib.dravaib.model.Country;
import com.dravaib.dravaib.repository.CountryRepository;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DravaibApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application.test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CountryControllerTests {
	@Autowired
	private CountryRepository repository;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CountryFactory countryFactory;

	@Test
	public void test1() throws Exception {
		Country country = countryFactory.make();
		Country country2 = countryFactory.make();
		repository.save(country);
		repository.save(country2);

		mvc.perform(get("/api/country")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is(country.getName())))
				.andExpect(jsonPath("$[1].name", is(country2.getName()))).andReturn();
	}

}
