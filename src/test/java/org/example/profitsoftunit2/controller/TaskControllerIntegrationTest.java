package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.profitsoftunit2.model.dto.TaskDto;
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
class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	void createProject_withValid_BodyShouldReturnCreated() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.name("Test task")
				.projectId(1L)
				.assigneeId(2L)
				.reporterId(1L)
				.build();

		mockMvc.perform(post("/api/task")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}
}
