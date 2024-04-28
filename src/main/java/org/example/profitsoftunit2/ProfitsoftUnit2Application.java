package org.example.profitsoftunit2;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProfitsoftUnit2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProfitsoftUnit2Application.class, args);
	}

	@Bean
	public Workbook workbook() {
		return new XSSFWorkbook();
	}
}
