package org.example.profitsoftunit2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectPageSearchDto;
import org.example.profitsoftunit2.service.ProjectService;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

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
	public ProjectDto updateProject(@PathVariable("id") Long id, @RequestBody @Valid ProjectDto projectDto,
									BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect Project data", bindingResult);
		}

		return projectService.updateProjectById(projectDto, id);
	}

	@PatchMapping("/{id}")
	public ProjectDto addMemberToProject(@PathVariable("id") Long id, @RequestBody @Valid MemberDto memberDto,
										 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect Member data", bindingResult);
		}

		return projectService.addMemberToProject(memberDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Long deleteProject(@PathVariable("id") Long id) {
		return projectService.deleteProjectById(id);
	}

	@PostMapping("/_list")
	public List<ProjectDto> findAll(@RequestBody @Valid ProjectPageSearchDto projectPageSearchDto,
									BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new EntityValidationException("Incorrect search data", bindingResult);
		}

		return projectService.findAll(projectPageSearchDto);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String response = projectService.uploadDataFromFile(file);
		return ResponseEntity.ok().body(response);
	}
}
