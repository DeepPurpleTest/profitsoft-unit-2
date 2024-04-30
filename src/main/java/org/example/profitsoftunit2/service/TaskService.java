package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.TaskDto;
import org.example.profitsoftunit2.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

	Optional<Task> findById(Long id);

	void createTask(TaskDto taskDto);

	List<TaskDto> findAll();

	List<Task> findAllByIdList(List<Long> ids);

	void deleteById(Long id);

	TaskDto updateTaskById(TaskDto taskDto, Long id);
}
