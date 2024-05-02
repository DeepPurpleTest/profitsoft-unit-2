package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Service responsible for generating Excel files based on project data.
 */

public interface FileGeneratorService {

	/**
	 * Creates a ResponseEntity containing an Excel file with project data
	 *
	 * @param projectsSearchDto the DTO containing search criteria for filtering projects
	 * @param type              the type of objects to be written to the Excel file
	 * @param sheetName         the name of the Excel sheet
	 * @return a ResponseEntity containing the generated Excel file
	 */
	ResponseEntity<byte[]> createExcelFileResponse(ProjectsSearchDto projectsSearchDto,
												   Class<?> type,
												   String sheetName,
												   String fileName)
			throws IllegalAccessException, IOException;
}
