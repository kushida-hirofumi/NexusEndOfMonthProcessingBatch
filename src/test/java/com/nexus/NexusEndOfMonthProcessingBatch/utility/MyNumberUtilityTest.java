package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MyNumberUtilityのテスト")
@Slf4j
public class MyNumberUtilityTest {

    @Test
    @DisplayName("nullCheckIntのテスト")
    public void nullCheckInt() {
        assertEquals(MyNumberUtility.nullCheckInt(null), 0);
        assertEquals(MyNumberUtility.nullCheckInt(0), 0);
        assertEquals(MyNumberUtility.nullCheckInt(100), 100);
    }

    @Test
    @DisplayName("nullCheckDoubleのテスト")
    public void nullCheckDouble() {
        assertEquals(MyNumberUtility.nullCheckDouble((Integer) null), 0);
        assertEquals(MyNumberUtility.nullCheckDouble((Double) null), 0);
        assertEquals(MyNumberUtility.nullCheckDouble(0), 0);
        assertEquals(MyNumberUtility.nullCheckDouble(100), 100);
        assertEquals(MyNumberUtility.nullCheckDouble(50.5), 50.5);
    }

    @Test
    @DisplayName("計算処理のテスト")
    public void calcStringByFormat() {

        assertEquals(mathSheet02SubIrregularInput(null, null), 0.0);

        assertEquals(mathSheet02SubIrregularInput("", null), 0.0);

        assertEquals(mathSheet02SubIrregularInput("", 1), 0.0);

        assertEquals(mathSheet02SubIrregularInput(" ", null), 0.0);

        assertEquals(mathSheet02SubIrregularInput(" ", 1), 0.0);

        assertEquals(mathSheet02SubIrregularInput("1", null), 0.0);

        assertEquals(mathSheet02SubIrregularInput(null, 1), 0.0);

        assertEquals(mathSheet02SubIrregularInput("1", 100), 100.0);

        assertEquals(mathSheet02SubIrregularInput("3", 5500), 16500.0);

        assertEquals(mathSheet02SubIrregularInput("1/2", 100), 50.0);

        assertEquals(mathSheet02SubIrregularInput("1/2", 1), 0.5);
    }

    double mathSheet02SubIrregularInput(String quantity, Integer unitPrice) {
        String format = "${quantity} * ${unitPrice}";
        NexusEndOfMonthProcessingSheet02SubIrregularEntity result = new NexusEndOfMonthProcessingSheet02SubIrregularEntity();
        result.setQuantity(quantity);
        result.setUnitPrice(unitPrice);
        return MyNumberUtility.calcStringByFormat(format, result);
    }
}