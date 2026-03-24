package com.nexus.NexusEndOfMonthProcessingBatch;

import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiAccessTokenTest;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiCompaniesTest;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiEmployeesTest;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtilityTest;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyDateUtilityTest;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyNumberUtilityTest;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtilityTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
		FreeeApiAccessTokenTest.class,
		FreeeApiCompaniesTest.class,
		FreeeApiEmployeesTest.class,
		FreeeApiAccessTokenTest.class,
		InterestRateUtilityTest.class,
		MyDateUtilityTest.class,
		MyNumberUtilityTest.class,
		MyStringUtilityTest.class
})
class NexusEndOfMonthProcessingBatchApplicationTests {
	@Test
	void contextLoads() {
	}
}