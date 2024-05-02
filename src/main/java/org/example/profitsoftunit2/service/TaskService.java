package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.TaskDto;

import java.util.List;

/**
 * Service responsible for performing operations with Member entities
 */
public interface TaskService {

	void createTask(TaskDto taskDto);

	List<TaskDto> findAll();

	void deleteById(Long id);

	/**
	 * Method for update Task by id <br/>
	 * Change project id not allowed
	 */
	TaskDto updateTaskById(TaskDto taskDto, Long id);
}
