package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.service.ExcelService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.example.profitsoftunit2.util.ReflectionUtils.getFieldValueByName;
import static org.example.profitsoftunit2.util.ReflectionUtils.getNames;
import static org.example.profitsoftunit2.util.ReflectionUtils.getObjectFields;
import static org.example.profitsoftunit2.util.ReflectionUtils.isTypeCollection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

	public byte[] generateFile(List<Project> projects) throws IllegalAccessException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Projects");

		createHeaderRow(sheet);
		writeDataRows(sheet, projects);

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

		int cellIndex = 0;
		for (String cellName : cellsNames) {
			Cell headerCell = header.createCell(cellIndex);
			headerCell.setCellValue(cellName);
			cellIndex++;
		}
	}

	private void writeDataRows(Sheet sheet, List<Project> projects) throws IllegalAccessException {
		Field[] entityFields = getObjectFields(Project.class);
		for (int i = 0; i < projects.size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < entityFields.length; j++) {
				Cell cell = row.createCell(j);
				Field field = entityFields[j];
				Object value;
				if (isTypeCollection(field.getType())) {
					Collection<?> fieldValue = (Collection<?>) field.get(projects.get(i));
					value = mapCollectionToString(fieldValue);
				} else {
					value = field.get(projects.get(i));
				}

				cell.setCellValue(value != null ? value.toString() : "");
			}
		}
	}

	private String mapCollectionToString(Collection<?> collection) {
		return collection.stream()
				.map(object -> getFieldValueByName(object, "name"))
				.filter(Objects::nonNull)
				.map(Object::toString)
				.collect(joining(", "));
	}
}
