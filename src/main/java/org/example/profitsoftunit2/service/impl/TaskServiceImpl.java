package org.example.profitsoftunit2.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.mapper.TaskMapper;
import org.example.profitsoftunit2.model.dto.TaskDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.model.entity.Task;
import org.example.profitsoftunit2.repository.TaskRepository;
import org.example.profitsoftunit2.service.MemberService;
import org.example.profitsoftunit2.service.ProjectService;
import org.example.profitsoftunit2.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final ProjectService projectService;
	private final MemberService memberService;
	private final TaskMapper taskMapper;

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	@Transactional
	public void createTask(TaskDto taskDto) {
		Task task = taskMapper.toEntity(taskDto);

		setUpTask(task);

		taskRepository.save(task);
	}

	@Override
	public List<TaskDto> findAll() {
		return taskMapper.mapAllToDto(taskRepository.findAll());
	}

	@Override
	public List<Task> findAllByIdList(List<Long> ids) {
		return taskRepository.findAllById(ids);
	}

	@Override
	public Long deleteById(Long id) {
		if (!taskRepository.existsById(id)) {
			throw new EntityNotFoundException(String.format("Task with id:%d is not found", id));
		}

		taskRepository.deleteById(id);
		return id;
	}

	//TODO all field requested and need to check if the are relevant
	@Override
	public void updateTaskById(TaskDto taskDto, Long id) {
	}

	//TODO check members in current project
	private void setUpTask(Task task) {
		Optional<Project> byId = projectService.findById(task.getProject().getId());
		Optional<Member> reporterById = memberService.findById(task.getReporter().getId());
		Optional<Member> assigneeById = Optional.ofNullable(task.getAssignee().getId())
				.flatMap(memberService::findById);

		task.setAssignee(assigneeById.orElse(null));
		task.setProject(byId.orElseThrow(() -> new EntityNotFoundException(
				String.format("Project with id: %d", task.getProject().getId()))));
		task.setReporter(reporterById.orElseThrow(() -> new EntityNotFoundException(
				String.format("Member reporter with id: %d", task.getProject().getId()))));
	}
}
