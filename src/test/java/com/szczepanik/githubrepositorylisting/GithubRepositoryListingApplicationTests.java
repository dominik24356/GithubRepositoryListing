package com.szczepanik.githubrepositorylisting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GithubRepositoryListingApplicationTests {


	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
	}

	@Test
	public void shouldReturnEmptyListWhenUserHasNoRepositories() throws Exception {

		String username = "user";
		 mockMvc.perform(get("/api/repository/" + username))
		         .andExpect(status().isOk())
		         .andExpect(content().string("[]"));

	}

}
