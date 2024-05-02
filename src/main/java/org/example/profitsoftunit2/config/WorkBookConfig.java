package org.example.profitsoftunit2.config;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Workbook configuration class ?????
 */
@Configuration
public class WorkBookConfig {

	@Bean
	public Workbook workbook() {
		return new XSSFWorkbook();
	}
}
