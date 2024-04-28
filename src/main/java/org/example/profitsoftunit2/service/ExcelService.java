package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.entity.Project;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
	File writeProjectsInFile(List<Project> projects) throws IllegalAccessException, IOException;
}
