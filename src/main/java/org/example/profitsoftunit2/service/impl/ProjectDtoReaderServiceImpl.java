package org.example.profitsoftunit2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.service.AbstractReaderService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectDtoReaderServiceImpl extends AbstractReaderService<ProjectDto> {
	public ProjectDtoReaderServiceImpl(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	public Class<ProjectDto> getType() {
		return ProjectDto.class;
	}
}
