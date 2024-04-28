package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.entity.Project;

import java.io.IOException;
import java.util.List;

public interface ExcelService {
	byte[] generateFile(List<Project> projects) throws IllegalAccessException, IOException;
}
