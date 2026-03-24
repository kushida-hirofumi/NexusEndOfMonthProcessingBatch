package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("MyDateUtilityのテスト")
@Slf4j
public class MyDateUtilityTest {

    @Test
    @DisplayName("nowLocalDateのテスト")
    public void nowLocalDate() {
        assertNotNull(MyDateUtility.nowLocalDate());
    }

    @Test
    @DisplayName("nowLocalDateTimeのテスト")
    public void nowLocalDateTime() {
        assertNotNull(MyDateUtility.nowLocalDateTime());
    }

}