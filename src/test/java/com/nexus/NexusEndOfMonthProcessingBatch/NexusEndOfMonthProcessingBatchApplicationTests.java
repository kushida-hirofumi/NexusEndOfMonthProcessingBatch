package com.nexus.NexusEndOfMonthProcessingBatch;

import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.FreeeApiRestTemplateTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
	FreeeApiRestTemplateTest.class,
})
class NexusEndOfMonthProcessingBatchApplicationTests {
	@Test
	void contextLoads() {
	}
}