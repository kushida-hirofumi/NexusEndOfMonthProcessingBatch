package com.nexus.NexusEndOfMonthProcessingBatch;

import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiAccessTokenTest;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiCompaniesTest;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiEmployeesTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
		FreeeApiAccessTokenTest.class,
		FreeeApiCompaniesTest.class,
		FreeeApiEmployeesTest.class,
		FreeeApiAccessTokenTest.class
})
class NexusEndOfMonthProcessingBatchApplicationTests {
	@Test
	void contextLoads() {
	}
}