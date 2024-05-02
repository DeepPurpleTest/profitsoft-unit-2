package org.example.profitsoftunit2.processor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.service.ReaderService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for reading data from files
 * This class provides a template for processing files and extracting data
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractFileProcessor<T> implements FileProcessor<T> {

	protected final ReaderService<T> readerService;

	@Override
	public List<T> extractDataFromFile(MultipartFile file) {
		List<T> projectList = new ArrayList<>();

		try (InputStream is = readerService.getStreamOfData(file)) {
			log.info("Extract data from file: " + file.getName());
			projectList = readerService.parseJson(is);
		} catch (IOException e) {
			log.warn("Cannot extract data from file: {}. Exception message: {}",
					file.getName(), e.getMessage());
		}

		return projectList;
	}
}
