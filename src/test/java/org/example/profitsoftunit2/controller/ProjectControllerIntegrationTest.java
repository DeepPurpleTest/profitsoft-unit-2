package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.example.profitsoftunit2.model.dto.ImportDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectsResponseDto;
import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.example.profitsoftunit2.model.dto.SimpleProjectDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("tc-jdbc")
@AutoConfigureMockMvc
class ProjectControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	@Transactional
	void createProject_withValidBody_shouldReturnCreatedAndStatusCreated() throws Exception {
		ProjectDto body = ProjectDto.builder()
				.name("Test project")
				.description("Test description")
				.build();

		mockMvc.perform(post("/api/project")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	void findProject_withValidId_shouldReturnDtoAndStatusOk() throws Exception {
		Long id = 1L;
		MvcResult result = mockMvc.perform(get("/api/project/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists())
				.andExpect(jsonPath("tasks").exists())
				.andExpect(jsonPath("members").exists())
				.andReturn();

		String stringProject = result.getResponse().getContentAsString();
		ProjectDto projectDto = objectMapper.readValue(stringProject, ProjectDto.class);
		assertEquals(id, projectDto.getId());
	}

	@Test
	@Transactional
	void updateProject_withValidBody_shouldReturnUpdatedDtoAndStatusOk() throws Exception {
		ProjectDto body = ProjectDto.builder()
				.id(1L)
				.name("New test name")
				.description("New test description")
				.build();

		MvcResult result = mockMvc.perform(put("/api/project/" + body.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andReturn();

		String stringProject = result.getResponse().getContentAsString();
		ProjectDto updatedProject = objectMapper.readValue(stringProject, ProjectDto.class);

		assertEquals(body.getId(), updatedProject.getId());
		assertEquals(body.getName(), updatedProject.getName());
		assertEquals(body.getDescription(), updatedProject.getDescription());
	}

	@Test
	@Transactional
	void deleteProjectById_withValidId_shouldReturnStatusOk() throws Exception {
		long id = 1L;

		mockMvc.perform(delete("/api/project/" + id))
				.andExpect(status().isOk());
	}

	@Test
	void findAll_shouldReturnProjectsWithPageSize() throws Exception {
		ProjectsSearchDto body = ProjectsSearchDto.builder()
				.projectName("Project 1")
				.membersIds(List.of(1L, 2L))
				.membersNames(List.of("Moksem", "Vodem"))
				.offset(0)
				.pageSize(5)
				.build();

		MvcResult result = mockMvc.perform(post("/api/project/_list")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body))
						.accept(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("projects").exists())
				.andExpect(jsonPath("total_pages").exists())
				.andReturn();

		String stringProjectsResponseDto = result.getResponse().getContentAsString();
		ProjectsResponseDto projectsResponseDto = objectMapper.readValue(stringProjectsResponseDto, ProjectsResponseDto.class);
		List<String> projectNames = projectsResponseDto.getProjects().stream()
				.map(SimpleProjectDto::getName)
				.toList();

		assertEquals(1, projectsResponseDto.getTotalPages());
		assertTrue(projectNames.contains(body.getProjectName()));
	}

	@Test
	@Transactional
	void testUploadFile_withValidFile_shouldReturnImportDto() throws Exception {
		ClassPathResource resource = new ClassPathResource("file/data.json");
		MockMultipartFile file = new MockMultipartFile("file", resource.getFilename(),
				MediaType.APPLICATION_JSON_VALUE, resource.getInputStream());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(multipart("/api/project/upload")
						.file(file))
				.andExpect(status().isOk())
				.andReturn();

		String stringImportDto = result.getResponse().getContentAsString();
		ImportDto importDto = objectMapper.readValue(stringImportDto, ImportDto.class);

		assertEquals(3, importDto.getSuccessfullySaved());
		assertEquals(0, importDto.getFailed());
	}

	@Test
	void generateReport_shouldReturnValidExcelFile() throws Exception {
		ProjectsSearchDto body = ProjectsSearchDto.builder()
				.build();

		MvcResult result = mockMvc.perform(post("/api/project/_report")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body))
						.accept(MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andReturn();

		byte[] content = result.getResponse().getContentAsByteArray();

		System.out.println(Arrays.toString(content));

		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		Workbook workbook = WorkbookFactory.create(bis);

		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row projectRow1 = sheet.getRow(1);
		Row projectRow2 = sheet.getRow(2);

		assertEquals("id", headerRow.getCell(0).getStringCellValue());
		assertEquals("name", headerRow.getCell(1).getStringCellValue());
		assertEquals("description", headerRow.getCell(2).getStringCellValue());
		assertEquals("tasks", headerRow.getCell(3).getStringCellValue());
		assertEquals("members", headerRow.getCell(4).getStringCellValue());

		assertEquals("1", projectRow1.getCell(0).getStringCellValue());
		assertEquals("Project 1", projectRow1.getCell(1).getStringCellValue());
		assertEquals("project 1", projectRow1.getCell(2).getStringCellValue());
		assertEquals("Project 1 Task 1", projectRow1.getCell(3).getStringCellValue());
		assertEquals("Moksem, Vodem", projectRow1.getCell(4).getStringCellValue());

		assertEquals("2", projectRow2.getCell(0).getStringCellValue());
		assertEquals("Project 2", projectRow2.getCell(1).getStringCellValue());
		assertEquals("project 2", projectRow2.getCell(2).getStringCellValue());
		assertEquals("Project 2 Task 1", projectRow2.getCell(3).getStringCellValue());
		assertEquals("Denchik, Maus", projectRow2.getCell(4).getStringCellValue());

		assertNull(sheet.getRow(3));
	}
}

