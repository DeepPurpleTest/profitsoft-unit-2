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

/**
 * Service responsible for performing operations with Project entities
 */
public interface ProjectService {

	void createProject(ProjectSaveDto projectDto);

	ProjectDto findProjectById(Long id);

	Optional<Project> findById(Long id);

	/**
	 * Method for update Project by id
	 * Include update name, description and members of project <br/>
	 * Task update not allowed
	 */
	ProjectDto updateProjectById(ProjectDto projectDto, Long id);

	void deleteProjectById(Long id);

	/**
	 * Method supports filtering by three fields: projectName, membersIds, membersNames with pagination <br/>
	 * All filters are applied using the "AND" query logic
	 * For example, a project with a specified name and not containing all specified member IDs will not be returned
	 */
	ProjectsResponseDto findAllWithFiltrationAndPagination(ProjectsSearchDto searchDto);

	/**
	 * Method supports filtering by three fields: projectName, membersIds, membersNames
	 * All filters are applied using the "AND" query logic
	 * For example, a project with a specified name and not containing all specified member IDs will not be returned
	 */
	List<ProjectDto> findAll(ProjectsSearchDto searchDto);

	/**
	 * Method for uploading data from a file to the database
	 * This method can create projects with members, but tasks are not allowed
	 * If the linked member entity is not found in the database, the project will not be saved
	 */
	ImportDto uploadDataFromFileToDb(MultipartFile multipartFile);
}
