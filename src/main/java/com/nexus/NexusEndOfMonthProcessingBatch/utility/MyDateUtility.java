package com.nexus.NexusEndOfMonthProcessingBatch.utility;


import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 時間関連の関数を定義したクラス
 */
public class MyDateUtility {

    public static final String LOCALE_ZONE = "Asia/Tokyo";

    /**
     * 現在時刻のLocalDateを取得する
     */
    public static LocalDate nowLocalDate() {
        return LocalDateTime.now(ZoneId.of(LOCALE_ZONE)).toLocalDate();
    }

    /**
     * 現在時刻のLocalDateTimeを取得する
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(LOCALE_ZONE));
    }

    /**
     * LocalDateTime型をミリ単位に変換
     */
    public static long localDateTimeToMili(LocalDateTime ldt) {
        if(ldt==null) return 0;
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(LOCALE_ZONE));
        return zdt.toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime型をString形式に変換
     */
    public static String localDateTimeToString(LocalDateTime ldt, String format) {
        if (ldt==null || StringUtils.isBlank(format)) return "";
        return ldt.format(DateTimeFormatter.ofPattern(format, Locale.JAPANESE));
    }

    /**
     * LocalDateTime型をLocalDate型にフォーマットする
     */
    public static LocalDate localDateTimeToLocalDate(LocalDateTime ld) {
        return ld.toLocalDate();
    }

    /**
     * LocalDate型をLocalDateTime型にフォーマットする
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate ld) {
        if(ld==null) return null;
        return LocalDateTime.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth(), 0, 0);
    }

    /**
     * LocalDate型をString形式に変換
     */
    public static String localDateToString(LocalDate ld, String format) {
        if (ld==null || StringUtils.isBlank(format)) return "";
        return ld.format(DateTimeFormatter.ofPattern(format, Locale.JAPANESE));
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String localDateToJapaneseFormatText(LocalDate ld, String format) {
        if(ld==null) return "";
        Locale locale = new Locale("ja", "JP", "JP");
        JapaneseDate japaneseDate = JapaneseDate.of(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return japaneseDate.format(formatter);
    }

}