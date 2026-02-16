package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * 数値関連の処理のユーティリティ
 */
@Slf4j
public class MyNumberUtility {

    public static int nullCheckInt(Integer value) {
        return value!=null ? value : 0;
    }

    public static double nullCheckDouble(Integer value) {
        return value!=null ? value : 0;
    }

    public static double nullCheckDouble(Double value) {
        return value!=null ? value : 0;
    }

    /**
     * 計算処理用
     * @param format    計算フォーマット
     * @param target    計算処理を行うオブジェクト
     * @return  計算結果
     */
    public static double calcStringByFormat(String format, Object target) {
        if (StringUtils.isBlank(format) || target==null) return 0;
        String exp = format;
        int start, end;
        HashMap<String, String> hashMap = new HashMap<>();
        do {
            start = exp.indexOf("${");
            end = exp.indexOf("}");
            if(start == -1 || end == -1) break;
            String fieldName = exp.substring(start + 2, end);
            exp = exp.substring(end + 1);
            try {
                if(!hashMap.containsKey(fieldName)) {
                    Object fieldValue = MyObjectUtility.getFieldValue(target, fieldName);
                    if(fieldValue!=null) hashMap.put(fieldName, "" + fieldValue);
                    else hashMap.put(fieldName, "0");
                }
            } catch (IllegalAccessException e) {
                log.warn(e.getMessage());
            }
        } while (true);
        exp = format;
        for(String key : hashMap.keySet()) {
            exp = exp.replace("${" + key + "}", hashMap.get(key));
        }
        try {
            Expression expression = new ExpressionBuilder(exp).build();
            return expression.evaluate();
        } catch (ArithmeticException | IllegalArgumentException e) {
            log.warn(e.getMessage());
            return 0;
        }
    }

}