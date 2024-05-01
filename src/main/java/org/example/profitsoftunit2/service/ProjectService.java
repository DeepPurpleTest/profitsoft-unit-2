package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.ImportDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectSaveDto;
import org.example.profitsoftunit2.model.dto.ProjectsResponseDto;
import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

	void createProject(ProjectSaveDto projectDto);

	ProjectDto findProjectById(Long id);

	Optional<Project> findById(Long id);

	ProjectDto updateProjectById(ProjectDto projectDto, Long id);

	void deleteProjectById(Long id);

	ProjectsResponseDto findAllWithPagination(ProjectsSearchDto searchDto);

	List<ProjectDto> findAll(ProjectsSearchDto searchDto);

	ImportDto uploadDataFromFileToDb(MultipartFile multipartFile);
}
