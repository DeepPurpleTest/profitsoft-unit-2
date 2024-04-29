package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("tc-jdbc")
@AutoConfigureMockMvc
class ProjectControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	void createProject_withValid_BodyShouldReturnCreated() throws Exception {
		ProjectDto projectDto = ProjectDto.builder()
				.name("Test project")
				.description("Test description")
				.build();

		mockMvc.perform(post("/api/project")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(projectDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}
}

