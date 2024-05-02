package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.profitsoftunit2.model.dto.MemberDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MemberControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void findAll_shouldReturnList() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/members"))
				.andExpect(status().isOk())
				.andReturn();

		String stringMembersDtoList = result.getResponse().getContentAsString();
		List<MemberDto> members = objectMapper.readValue(stringMembersDtoList, new TypeReference<>(){});

		assertNotNull(members);
		assertEquals(4, members.size());
	}

	@Test
	@Transactional
	void createMember_withValidBody_shouldReturnCreated() throws Exception {
		MemberDto memberDto = MemberDto.builder()
				.name("Test member")
				.email("test@gmail.com")
				.build();

		mockMvc.perform(post("/api/members")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	@Transactional
	void createMember_withExistingEmail_shouldReturnException() throws Exception {
		MemberDto memberDto = MemberDto.builder()
				.name("Test member")
				.email("moksem@gmail.com")
				.build();

		mockMvc.perform(post("/api/members")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	@Transactional
	void updateMember_shouldReturnUpdatedDto() throws Exception {
		MemberDto memberDto = MemberDto.builder()
				.id(1L)
				.name("Maksym")
				.email("maksym@gmail.com")
				.build();

		MvcResult result = mockMvc.perform(put("/api/members/" + memberDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberDto))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("email").exists())
				.andExpect(jsonPath("projectsIds").exists())
				.andReturn();

		String stringMemberDto = result.getResponse().getContentAsString();
		MemberDto updatedMember = objectMapper.readValue(stringMemberDto, MemberDto.class);

		assertEquals(memberDto.getId(), updatedMember.getId());
		assertEquals(memberDto.getName(), updatedMember.getName());
		assertEquals(memberDto.getEmail(), updatedMember.getEmail());
	}

	@Test
	@Transactional
	void deleteMember_withValidId_shouldReturnStatusOk() throws Exception {
		long id = 1L;

		mockMvc.perform(delete("/api/members/" + id))
				.andExpect(status().isOk())
				.andReturn();
	}
}
