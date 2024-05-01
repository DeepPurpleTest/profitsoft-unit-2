package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.TaskDto;

import java.util.List;

public interface TaskService {

	void createTask(TaskDto taskDto);

	List<TaskDto> findAll();

	void deleteById(Long id);

	TaskDto updateTaskById(TaskDto taskDto, Long id);
}
