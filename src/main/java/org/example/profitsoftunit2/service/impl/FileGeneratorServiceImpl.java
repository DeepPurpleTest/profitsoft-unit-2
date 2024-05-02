package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.service.FileGeneratorService;
import org.example.profitsoftunit2.service.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.example.profitsoftunit2.util.reflection.ReflectionUtils.getFieldValueByName;
import static org.example.profitsoftunit2.util.reflection.ReflectionUtils.getNames;
import static org.example.profitsoftunit2.util.reflection.ReflectionUtils.getObjectFields;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileGeneratorServiceImpl implements FileGeneratorService {

	private final ProjectService projectService;

	@Override
	public ResponseEntity<byte[]> createExcelFileResponse(ProjectsSearchDto projectsSearchDto,
														  Class<?> type,
														  String sheetName,
														  String fileName) throws IllegalAccessException {
		List<Object> objects = new ArrayList<>(projectService.findAll(projectsSearchDto));

		byte[] fileContent = generateFile(objects, type, sheetName);

		HttpHeaders headers = populateHeaders(fileName);

		return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}

	private static HttpHeaders populateHeaders(String fileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return headers;
	}

	private byte[] generateFile(List<Object> objects, Class<?> type, String sheetName) throws IllegalAccessException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);

		createHeaderRow(sheet);
		writeDataRows(sheet, objects, type);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			workbook.write(outputStream);
			workbook.close();

			return outputStream.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage());
			return new byte[0];
		}
	}

	private void createHeaderRow(Sheet sheet) {
		Row header = sheet.createRow(0);
		List<String> cellsNames = getNames(Project.class);

		for (int i = 0; i < cellsNames.size(); i++) {
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(cellsNames.get(i));
		}
	}

	private void writeDataRows(Sheet sheet, List<Object> objects, Class<?> type) throws IllegalAccessException {
		Field[] entityFields = getObjectFields(type);
		for (int i = 0; i < objects.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Object object = objects.get(i);
			for (int j = 0; j < entityFields.length; j++) {
				Cell cell = row.createCell(j);
				fillCell(cell, entityFields[j], object);
			}
		}
	}

	private void fillCell(Cell cell, Field field, Object object) throws IllegalAccessException {
		Object value = field.get(object);
		if (value instanceof Collection<?> collectionValue) {
			value = mapCollectionToString(collectionValue);
		}

		String cellValue = (value != null) ? value.toString() : "";
		cell.setCellValue(cellValue);
	}

	private String mapCollectionToString(Collection<?> collection) {
		return collection.stream()
				.map(object -> getFieldValueByName(object, "name"))
				.filter(Objects::nonNull)
				.map(Object::toString)
				.sorted()
				.collect(joining(", "));
	}
}
