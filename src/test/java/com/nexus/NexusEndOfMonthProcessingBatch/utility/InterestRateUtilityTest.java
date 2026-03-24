package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("InterestRateUtilityのテスト")
@Slf4j
public class InterestRateUtilityTest {

    @Test
    @DisplayName("percentのテスト")
    public void percent() {
        assertEquals(InterestRateUtility.percent(1000, 100), 1000);
        assertEquals(InterestRateUtility.percent(1000, 50), 500);
        assertEquals(InterestRateUtility.percent(1000.555, 100), 1000.555);
        assertEquals(InterestRateUtility.percent(1000.555, 50), 500.2775);
    }

    @Test
    @DisplayName("切り捨て計算のテスト")
    public void roundDown() {
        assertEquals(InterestRateUtility.roundDown(0, 1), 0);
        assertEquals(InterestRateUtility.roundDown(0, 2), 0);
        assertEquals(InterestRateUtility.roundDown(-100, 1), -100);
        assertEquals(InterestRateUtility.roundDown(1000, 1), 1000);


        assertEquals(InterestRateUtility.roundDown(1000.555, 0), 1000);
        assertEquals(InterestRateUtility.roundDown(1000.555, 1), 1000.5);
        assertEquals(InterestRateUtility.roundDown(1000.555, 2), 1000.55);
        assertEquals(InterestRateUtility.roundDown(1000.555, 3), 1000.555);
        assertEquals(InterestRateUtility.roundDown(1000.555, 4), 1000.555);
        assertEquals(InterestRateUtility.roundDown(1000.555, 5), 1000.555);
    }

    @Test
    @DisplayName("四捨五入計算のテスト")
    public void roundUp() {
        assertEquals(InterestRateUtility.roundUp(0, 0), 0);
        assertEquals(InterestRateUtility.roundUp(0, 1), 0);

        assertEquals(InterestRateUtility.roundUp(1000.444, 0), 1000);
        assertEquals(InterestRateUtility.roundUp(1000.444, 1), 1000.4);
        assertEquals(InterestRateUtility.roundUp(1000.444, 2), 1000.44);
        assertEquals(InterestRateUtility.roundUp(1000.444, 3), 1000.444);
        assertEquals(InterestRateUtility.roundUp(1000.444, 4), 1000.444);

        assertEquals(InterestRateUtility.roundUp(1000.555, 0), 1001);
        assertEquals(InterestRateUtility.roundUp(1000.555, 1), 1000.6);
        assertEquals(InterestRateUtility.roundUp(1000.555, 2), 1000.56);
        assertEquals(InterestRateUtility.roundUp(1000.555, 3), 1000.555);
        assertEquals(InterestRateUtility.roundUp(1000.555, 4), 1000.555);


        assertEquals(InterestRateUtility.roundUp(1000.565, 1), 1000.6);
    }

}