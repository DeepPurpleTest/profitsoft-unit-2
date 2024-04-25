package org.example.profitsoftunit2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Provides interface for work with json data
 */
public interface ReaderService<T> {
	InputStream getStreamOfData(MultipartFile file) throws IOException;

	List<T> parseJson(InputStream data) throws IOException;
}
