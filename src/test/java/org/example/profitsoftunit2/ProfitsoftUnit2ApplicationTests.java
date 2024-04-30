package org.example.profitsoftunit2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("tc-jdbc")
@AutoConfigureMockMvc
class ProfitsoftUnit2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
