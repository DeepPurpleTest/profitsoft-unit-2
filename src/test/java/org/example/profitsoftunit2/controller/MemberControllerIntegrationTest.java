package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.profitsoftunit2.model.dto.MemberDto;
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
class MemberControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	void createProject_withValid_BodyShouldReturnCreated() throws Exception {
		MemberDto memberDto = MemberDto.builder()
				.name("Test member")
				.build();

		mockMvc.perform(post("/api/member")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}
}
