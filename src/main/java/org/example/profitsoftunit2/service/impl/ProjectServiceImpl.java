package org.example.profitsoftunit2.service.impl;

import org.example.profitsoftunit2.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.mapper.ProjectMapper;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectPageSearchDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.processor.FileProcessor;
import org.example.profitsoftunit2.repository.ProjectRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.example.profitsoftunit2.service.ProjectService;
import org.example.profitsoftunit2.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final MemberService memberService;
	private final TaskService taskService;
	private final FileProcessor<ProjectDto> fileProcessor;
	private final ProjectMapper projectMapper;

	@Override
	public void createProject(ProjectDto projectDto) {
		Project project = projectMapper.toEntity(projectDto);

		projectRepository.save(project);
	}

	@Override
	public void createProjects(List<ProjectDto> projectsDto) {
		List<Project> projects = projectMapper.mapAllToEntity(projectsDto);
		log.info(projects.toString());

		//TODO save + validation of entities
		List<Project> successfullySaved = new ArrayList<>();
		for (Project project : projects) {
//			memberService.
			Project saved = projectRepository.save(project);
			successfullySaved.add(saved);
		}
//		List<Project> successfullySaved = projectRepository.saveAll(projects);
		log.info(String.valueOf(successfullySaved.size()));
	}

	@Override
	public ProjectDto findProjectById(Long id) {
		Optional<Project> byId = projectRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Project with id: %d not found", id));
		}

		return projectMapper.toDto(byId.get());
	}

	@Override
	public Optional<Project> findById(Long id) {
		return projectRepository.findById(id);
	}

	@Override
	public ProjectDto updateProjectById(ProjectDto projectDto, Long id) {
		Optional<Project> byId = projectRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Project with id: %d not found", id));
		}

		Project project = updateFields(projectDto, byId.get());
		Project updatedProject = projectRepository.save(project);

		return projectMapper.toDto(updatedProject);
	}

	@Override
	@Transactional
	public ProjectDto addMemberToProject(MemberDto memberDto, Long id) {
		Optional<Project> projectById = projectRepository.findById(id);
		if (projectById.isEmpty()) {
			throw new EntityNotFoundException(String.format("Project with id: %d not found", memberDto.getId()));
		}

		Optional<Member> memberById = memberService.findById(memberDto.getId());
		if (memberById.isEmpty()) {
			throw new EntityNotFoundException(String.format("Member with id: %d not found", memberDto.getId()));
		}

		Project project = projectById.get();
		Member member = memberById.get();
		project.getMembers().add(member);
		member.getProjects().add(project);

		return projectMapper.toDto(project);
	}

	@Override
	public Long deleteProjectById(Long id) {
		Optional<Project> byId = projectRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Project with id: %d not found", id));
		}

		projectRepository.deleteById(id);

		return id;
	}

	@Override
	public List<ProjectDto> findAll(ProjectPageSearchDto searchDto) {
		Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());

		return projectMapper.mapAllToDto(projectRepository.findAllBy(pageable));
	}

	@Override
	public String uploadDataFromFile(MultipartFile multipartFile) {
		List<ProjectDto> dtoProjects = fileProcessor.extractDataFromFile(multipartFile);
		log.info(dtoProjects.toString());

		createProjects(dtoProjects);
		return "extract data from file";
	}

	private Project updateFields(ProjectDto projectDto, Project project) {
		Project projectToUpdate = new Project();
		projectToUpdate.setId(project.getId());
		projectToUpdate.setName(projectDto.getName() == null? project.getName() : projectDto.getName());
		projectToUpdate.setDescription(projectDto.getDescription() == null?
				project.getDescription() : projectDto.getDescription());
		projectToUpdate.setMembers(project.getMembers());
		projectToUpdate.setTasks(project.getTasks());

		return projectToUpdate;
	}
}
