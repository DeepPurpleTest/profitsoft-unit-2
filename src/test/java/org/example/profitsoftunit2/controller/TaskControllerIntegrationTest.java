package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void findAll_shouldReturnList() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/tasks"))
				.andExpect(status().isOk())
				.andReturn();

		String stringMembersDtoList = result.getResponse().getContentAsString();
		List<MemberDto> members = objectMapper.readValue(stringMembersDtoList, new TypeReference<>(){});

		assertNotNull(members);
		assertEquals(2, members.size());
	}

	@Test
	@Transactional
	void createTask_withValid_BodyShouldReturnCreated() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.name("Test task")
				.projectId(1L)
				.assigneeId(2L)
				.reporterId(1L)
				.build();

		mockMvc.perform(post("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	@Transactional
	void updateTask_withValidData_shouldReturnUpdatedDto() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.id(1L)
				.name("Project 1 Task 1 updated")
				.description("Project 1 Task 1 updated")
				.projectId(1L)
				.assigneeId(1L)
				.reporterId(2L)
				.build();

		MvcResult result = mockMvc.perform(put("/api/tasks/" + taskDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists())
				.andExpect(jsonPath("assignee_id").exists())
				.andExpect(jsonPath("reporter_id").exists())
				.andExpect(jsonPath("project_id").exists())
				.andReturn();

		String stringTaskDto = result.getResponse().getContentAsString();
		TaskDto updatedTask = objectMapper.readValue(stringTaskDto, TaskDto.class);

		assertEquals(taskDto.getId(), updatedTask.getId());
		assertEquals(taskDto.getName(), updatedTask.getName());
		assertEquals(taskDto.getDescription(), updatedTask.getDescription());
		assertEquals(taskDto.getAssigneeId(), updatedTask.getAssigneeId());
		assertEquals(taskDto.getReporterId(), updatedTask.getReporterId());
	}

	@Test
	@Transactional
	void updateTask_withInvalidAssigneeId_shouldReturnException() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.id(1L)
				.name("Project 1 Task 1 updated")
				.description("Project 1 Task 1 updated")
				.reporterId(2L)
				.assigneeId(3L)
				.build();

		mockMvc.perform(put("/api/tasks/" + taskDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	@Transactional
	void updateTask_withInvalidReporterId_shouldReturnException() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.id(1L)
				.name("Project 1 Task 1 updated")
				.description("Project 1 Task 1 updated")
				.reporterId(3L)
				.assigneeId(1L)
				.build();

		mockMvc.perform(put("/api/tasks/" + taskDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	@Transactional
	void updateTask_withNullAssigneeId_shouldReturnUpdatedDto() throws Exception {
		TaskDto taskDto = TaskDto.builder()
				.id(1L)
				.name("Project 1 Task 1 updated")
				.description("Project 1 Task 1 updated")
				.projectId(1L)
				.reporterId(2L)
				.build();

		MvcResult result = mockMvc.perform(put("/api/tasks/" + taskDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists())
				.andExpect(jsonPath("reporter_id").exists())
				.andExpect(jsonPath("project_id").exists())
				.andReturn();

		String stringTaskDto = result.getResponse().getContentAsString();
		TaskDto updatedTask = objectMapper.readValue(stringTaskDto, TaskDto.class);

		assertEquals(taskDto.getId(), updatedTask.getId());
		assertEquals(taskDto.getName(), updatedTask.getName());
		assertEquals(taskDto.getDescription(), updatedTask.getDescription());
		assertEquals(taskDto.getReporterId(), updatedTask.getReporterId());
		assertNull(taskDto.getAssigneeId());
	}

	@Test
	@Transactional
	void deleteTask_withValidId_shouldReturnStatusOk() throws Exception {
		long id = 1L;

		mockMvc.perform(delete("/api/tasks/" + id))
				.andExpect(status().isOk())
				.andReturn();
	}
}
