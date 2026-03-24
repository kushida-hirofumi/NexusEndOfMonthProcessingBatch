package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MyStringUtilityのテスト")
@Slf4j
public class MyStringUtilityTest {

    @Test
    @DisplayName("deleteSpaceのテスト")
    public void deleteSpace() {
        assertEquals(MyStringUtility.deleteSpace("aa"), "aa");
        assertEquals(MyStringUtility.deleteSpace("a a"), "aa");
        assertEquals(MyStringUtility.deleteSpace("a a a"), "aaa");
        assertEquals(MyStringUtility.deleteSpace("a　a"), "aa");
        assertEquals(MyStringUtility.deleteSpace("a　a　a"), "aaa");
        assertEquals(MyStringUtility.deleteSpace("a a　a"), "aaa");
    }

    @Test
    @DisplayName("文字列の数値変換判定のテスト")
    public void isNumeric() {
        assertEquals(MyStringUtility.isNumeric("aa"), false);
        assertEquals(MyStringUtility.isNumeric("a1"), false);
        assertEquals(MyStringUtility.isNumeric("1/2"), false);
        assertEquals(MyStringUtility.isNumeric("1"), true);
        assertEquals(MyStringUtility.isNumeric("1.5"), true);
    }

    @Test
    @DisplayName("文字列をdouble型に変換する処理のテスト")
    public void parseDoubleByString() {
        assertEquals(MyStringUtility.parseDoubleByString("a"), 0);
        assertEquals(MyStringUtility.parseDoubleByString("a1"), 0);
        assertEquals(MyStringUtility.parseDoubleByString("1"), 1);
        assertEquals(MyStringUtility.parseDoubleByString("1.5"), 1.5);
    }

    @Test
    @DisplayName("文字列をint型に変換する処理のテスト")
    public void parseIntByString() {
        assertEquals(MyStringUtility.parseIntByString("a"), 0);
        assertEquals(MyStringUtility.parseIntByString("a1"), 0);
        assertEquals(MyStringUtility.parseIntByString("1"), 1);
        assertEquals(MyStringUtility.parseIntByString("1.5"), 1);
    }

    @Test
    @DisplayName("数値をカンマ入りの数字に変換する処理のテスト")
    public void translateToNumberComma() {
        assertEquals(MyStringUtility.translateToNumberCommaByInteger(0), "0");
        assertEquals(MyStringUtility.translateToNumberCommaByInteger(100), "100");
        assertEquals(MyStringUtility.translateToNumberCommaByInteger(1000), "1,000");
        assertEquals(MyStringUtility.translateToNumberCommaByInteger(1000), "1,000");

        assertEquals(MyStringUtility.translateToNumberCommaByDouble(0), "0");
        assertEquals(MyStringUtility.translateToNumberCommaByDouble(100), "100");
        assertEquals(MyStringUtility.translateToNumberCommaByDouble(1000), "1,000");
        assertEquals(MyStringUtility.translateToNumberCommaByDouble(1000.222), "1,000.222");
    }

}