package org.example.profitsoftunit2.service;

import java.io.IOException;
import java.util.List;

public interface ExcelService {
	byte[] generateFile(List<Object> objects, Class<?> type, String sheetName) throws IllegalAccessException, IOException;
}
