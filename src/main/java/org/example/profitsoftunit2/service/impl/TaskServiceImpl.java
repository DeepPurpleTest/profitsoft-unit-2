package org.example.profitsoftunit2.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.mapper.TaskMapper;
import org.example.profitsoftunit2.model.dto.TaskDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.model.entity.Task;
import org.example.profitsoftunit2.model.event.EventType;
import org.example.profitsoftunit2.model.event.TaskEvent;
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
	private final TaskEventServiceImpl taskEventService;
	private final TaskEventBuilderImpl taskEventBuilder;
	private final TaskMapper taskMapper;

	/**
	 * Save task and produce events for notify members
	 */
	@Override
	@Transactional
	public void createTask(TaskDto taskDto) {
		Task task = taskMapper.toEntity(taskDto);

		setUpTask(task);

		Task createdTask = taskRepository.save(task);

		List<TaskEvent> events = taskEventBuilder.buildEvents(createdTask, EventType.CREATE);
		taskEventService.produceEvents(events);
	}

	@Override
	public List<TaskDto> findAll() {
		return taskMapper.mapAllToDto(taskRepository.findAll());
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (!taskRepository.existsById(id)) {
			throw new EntityNotFoundException(String.format("Task with id:%d is not found", id));
		}

		taskRepository.deleteById(id);
	}

	@Override
	@Transactional
	public TaskDto updateTaskById(TaskDto taskDto, Long id) {
		Optional<Task> byId = taskRepository.findById(id);

		if (byId.isEmpty()) {
			throw new EntityNotFoundException(String.format("Task with id: %d not found", id));
		}

		Task task = updateFields(taskDto, byId.get());
		setUpTask(task);
		Task updatedTask = taskRepository.save(task);
		return taskMapper.toDto(updatedTask);
	}

	private Task updateFields(TaskDto taskDto, Task task) {
		Task taskToUpdate = new Task();
		taskToUpdate.setId(task.getId());
		taskToUpdate.setName(taskDto.getName() == null ? task.getName() : taskDto.getName());
		taskToUpdate.setDescription(taskDto.getDescription() == null ?
				task.getDescription() : taskDto.getDescription());
		taskToUpdate.setProject(task.getProject());
		taskToUpdate.setAssignee(Member.builder().id(taskDto.getAssigneeId()).build());
		taskToUpdate.setReporter(taskDto.getReporterId() == null ?
				task.getReporter() : Member.builder().id(taskDto.getReporterId()).build());

		return taskToUpdate;
	}


	private void setUpTask(Task task) {
		Optional<Project> byId = projectService.findById(task.getProject().getId());
		task.setProject(byId.orElseThrow(() -> new EntityValidationException(
				String.format("Project with id:%d not found", task.getProject().getId()))));

		Optional<Member> reporterById = memberService.findByIdAndProjectId(task.getReporter().getId(),
				task.getProject().getId());
		task.setReporter(reporterById.orElseThrow(() -> new EntityValidationException(
				String.format("Member reporter with id:%d not found", task.getReporter().getId()))));


		Member assignee = getAssigneeMember(task.getAssignee().getId(), task.getProject().getId());
		task.setAssignee(assignee);
	}

	private Member getAssigneeMember(Long assigneeId, Long projectId) {
		if(assigneeId == null) {
			return null;
		}

		Optional<Member> memberByIdAndProjectId = memberService.findByIdAndProjectId(assigneeId, projectId);
		return memberByIdAndProjectId.orElseThrow(() -> new EntityValidationException(
				String.format("Member assignee with id:%d not found", assigneeId)));
	}
}
