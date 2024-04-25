package org.example.profitsoftunit2.processor;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Provides interface for working with files
 */
public interface FileProcessor<T> {

	List<T> extractDataFromFile(MultipartFile file);
}
