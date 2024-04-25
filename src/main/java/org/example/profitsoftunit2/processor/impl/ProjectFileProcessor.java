package org.example.profitsoftunit2.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.processor.AbstractFileProcessor;
import org.example.profitsoftunit2.service.ReaderService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProjectFileProcessor extends AbstractFileProcessor<ProjectDto> {
	public ProjectFileProcessor(ReaderService<ProjectDto> readerService) {
		super(readerService);
	}
}
