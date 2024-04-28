package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.service.ExcelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.example.profitsoftunit2.util.ReflectionUtils.getFieldValueByName;
import static org.example.profitsoftunit2.util.ReflectionUtils.getNames;
import static org.example.profitsoftunit2.util.ReflectionUtils.getObjectFields;
import static org.example.profitsoftunit2.util.ReflectionUtils.isTypeCollection;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

	private final Workbook workbook;

	public File writeProjectsInFile(List<Project> projects) throws IllegalAccessException, IOException {
		Sheet sheet = workbook.createSheet("Projects");

		Row header = sheet.createRow(0);
		List<String> cellsNames = getNames(Project.class);

		int cellIndex = 0;

		for (String cellName : cellsNames) {
			Cell headerCell = header.createCell(cellIndex);
			headerCell.setCellValue(cellName);

			cellIndex++;
		}

		Field[] entityFields = getObjectFields(Project.class);
		for (int i = 0; i < projects.size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < cellsNames.size(); j++) {
				Cell cell = row.createCell(j);
				Field field = entityFields[j];

				Object value;
				if (isTypeCollection(field.getType())) {
					value = mapCollectionToString(Collections.singleton(field));
				} else {
					value = entityFields[j].get(projects.get(i));
				}
				cell.setCellValue(value != null ? value.toString() : "");
			}
		}

		String fileName = "output.xlsx";
		String filePath = System.getProperty("user.dir") + File.separator + fileName;

		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
		workbook.close();

		return null;
	}

	private String mapCollectionToString(Collection<?> collection) {
		return collection.stream()
				.map(object -> getFieldValueByName(object, "name"))
				.filter(Objects::nonNull)
				.map(Object::toString)
				.collect(joining(", "));
	}
}
