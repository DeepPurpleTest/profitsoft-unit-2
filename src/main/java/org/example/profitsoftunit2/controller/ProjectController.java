package org.example.profitsoftunit2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.model.dto.ImportDto;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectSearchDto;
import org.example.profitsoftunit2.service.ExcelService;
import org.example.profitsoftunit2.service.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;
	private final ExcelService excelService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createProject(@RequestBody @Valid ProjectDto projectDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect Project data", bindingResult);
		}

		projectService.createProject(projectDto);
	}

	@GetMapping("/{id}")
	public ProjectDto findProject(@PathVariable("id") Long id) {
		return projectService.findProjectById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProject(@PathVariable("id") Long id, @RequestBody @Valid ProjectDto projectDto,
									BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect Project data", bindingResult);
		}

		projectService.updateProjectById(projectDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Long deleteProject(@PathVariable("id") Long id) {
		return projectService.deleteProjectById(id);
	}

	@PostMapping("/_list")
	public List<ProjectDto> findAll(@RequestBody @Valid ProjectSearchDto projectSearchDto,
									BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect search data", bindingResult);
		}

		List<ProjectDto> projects = projectService.findAllWithPagination(projectSearchDto);
		log.info("size of projects {}", projects.size());
		return projects;
	}

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.OK)
	public ImportDto uploadFile(@RequestParam("file") MultipartFile file) {
		return projectService.uploadDataFromFileToDb(file);
	}

	@PostMapping(value = "_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> generateReport(@RequestBody @Valid ProjectSearchDto projectSearchDto,
												   BindingResult bindingResult) throws IllegalAccessException, IOException {
		if(bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect search data", bindingResult);
		}

		List<Object> objects = new ArrayList<>(projectService.findAll(projectSearchDto));
		byte[] fileContent = excelService.generateFile(objects, ProjectDto.class, "Projects");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "projects.xlsx");

		return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
}
