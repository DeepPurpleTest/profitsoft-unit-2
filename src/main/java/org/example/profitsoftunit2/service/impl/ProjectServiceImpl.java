package org.example.profitsoftunit2.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.mapper.ProjectMapper;
import org.example.profitsoftunit2.model.dto.ImportDto;
import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.dto.ProjectSearchDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.processor.FileProcessor;
import org.example.profitsoftunit2.repository.ProjectRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.example.profitsoftunit2.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final MemberService memberService;
	private final FileProcessor<ProjectDto> fileProcessor;
	private final ProjectMapper projectMapper;

	@Override
	public void createProject(ProjectDto projectDto) {
		Project project = projectMapper.toEntity(projectDto);

		projectRepository.save(project);
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

	//TODO all field requested and need to check if the are relevant
	@Override
	public void updateProjectById(ProjectDto projectDto, Long id) {
		Optional<Project> byId = projectRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Project with id: %d not found", id));
		}

		Project project = updateFields(projectDto, byId.get());
		projectRepository.save(project);
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
		if (!projectRepository.existsById(id)) {
			throw new EntityNotFoundException(String.format("Project with id:%d is not found", id));
		}

		projectRepository.deleteById(id);
		return id;
	}

	//TODO not all project fields
	@Override
	public List<ProjectDto> findAllWithPagination(ProjectSearchDto searchDto) {
		List<Project> projects = projectRepository.findWithFiltrationAndPagination(searchDto);

		return projectMapper.mapAllToDto(projects);
	}

	@Override
	public List<ProjectDto> findAll(ProjectSearchDto searchDto) {
		return projectMapper.mapAllToDto(projectRepository.findWithFiltration(searchDto));
	}

	@Override
	public List<Project> findAllByIds(List<Long> ids) {
		List<Project> existingProjects = projectRepository.findAllById(ids);
		Set<Long> existingIds = existingProjects.stream()
				.map(Project::getId)
				.collect(Collectors.toSet());

		if (existingIds.size() < ids.size()) {
			List<Long> notFoundIds = new ArrayList<>(ids);
			notFoundIds.removeAll(existingIds);
			throw new EntityNotFoundException(String.format("Projects with ids %s are not found", notFoundIds));
		}

		return existingProjects;
	}

	@Override
	@Transactional
	public ImportDto uploadDataFromFileToDb(MultipartFile multipartFile) {
		List<ProjectDto> dtoProjects = fileProcessor.extractDataFromFile(multipartFile);
		List<Project> projects = projectMapper.mapAllToEntity(dtoProjects);

		List<Project> validProjects = projects.stream()
				.filter(this::validateProject)
				.map(this::addDataToProject)
				.toList();
		List<Project> savedProjects = projectRepository.saveAllAndFlush(validProjects);

		log.info("Successfully saved projects: {}", savedProjects.size());
		return ImportDto.builder()
				.successfullySaved(savedProjects.size())
				.failed(projects.size() - savedProjects.size())
				.build();
	}

	private Project addDataToProject(Project project) {
		Set<Long> memberIds = project.getMembers().stream()
				.map(Member::getId)
				.collect(Collectors.toSet());
		Set<Member> members = new HashSet<>(memberService.findAllByIds(memberIds));

		project.setMembers(members);
		return project;
	}

	private boolean validateProject(Project project) {
		if (project.getId() != null && projectRepository.existsById(project.getId())) {
			log.warn("Project with id:{} is already exist", project.getId());
			return false;
		}

		for (Member member : project.getMembers()) {
			if (member.getId() == null || !memberService.existsById(member.getId())) {
				log.warn("Invalid member id:{}", member.getId());
				return false;
			}
		}

		return true;
	}

	private Project updateFields(ProjectDto projectDto, Project project) {
		Project projectToUpdate = new Project();
		projectToUpdate.setId(project.getId());
		projectToUpdate.setName(projectDto.getName() == null ? project.getName() : projectDto.getName());
		projectToUpdate.setDescription(projectDto.getDescription() == null ?
				project.getDescription() : projectDto.getDescription());
		projectToUpdate.setMembers(project.getMembers());
		projectToUpdate.setTasks(project.getTasks());

		return projectToUpdate;
	}
}
