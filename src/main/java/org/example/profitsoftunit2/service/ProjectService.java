package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.ImportDto;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

	void createProject(ProjectDto projectDto);

	ProjectDto findProjectById(Long id);

	Optional<Project> findById(Long id);

	ProjectDto updateProjectById(ProjectDto projectDto, Long id);

	ProjectDto addMemberToProject(MemberDto memberDto, Long id);

	Long deleteProjectById(Long id);

	List<ProjectDto> findAllWithPagination(ProjectSearchDto searchDto);

	List<ProjectDto> findAll(ProjectSearchDto searchDto);

	ImportDto uploadDataFromFileToDb(MultipartFile multipartFile);
}
