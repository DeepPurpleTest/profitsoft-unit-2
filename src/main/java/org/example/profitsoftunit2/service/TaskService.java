package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.TaskDto;
import org.example.profitsoftunit2.model.entity.Task;

import java.util.Optional;

public interface TaskService {

	Optional<Task> findById(Long id);

	void createTask(TaskDto taskDto);
}
