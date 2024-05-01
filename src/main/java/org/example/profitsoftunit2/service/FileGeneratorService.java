package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FileGeneratorService {
	ResponseEntity<byte[]> createExcelFileResponse(ProjectsSearchDto projectsSearchDto, Class<?> type, String sheetName)
			throws IllegalAccessException, IOException;
}
