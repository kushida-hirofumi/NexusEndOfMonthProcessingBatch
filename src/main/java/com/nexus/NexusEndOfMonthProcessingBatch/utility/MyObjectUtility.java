package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class MyObjectUtility {

    /**
     * オブジェクトが持つ全てのフィールドの名前と値をHashMapに格納して出力する
     * @return HashMapオブジェクト
     */
    public static HashMap<String, String> fieldsInHashMap(Object object) {
        if(object==null) return null;
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            Class<?> refCls = object.getClass();
            do {
                for (Field field : refCls.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) continue;
                    field.setAccessible(true);
                    if (field.get(object) == null) continue;
                    hashMap.put(field.getName(), String.valueOf(field.get(object)));
                }
                refCls = refCls.getSuperclass();
            } while (refCls != null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return hashMap;
    }

    public static Field getField(Object object,String fieldName) {
        if(object==null) return null;
        Class<?> refCls = object.getClass();
        if(refCls==null || StringUtils.isBlank(fieldName)) return null;
        do {
            for(Field field : refCls.getDeclaredFields()) {
                if(field.getName().equals(fieldName)) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        field.setAccessible(true);
                        return field;
                    }
                    break;
                }
            }
            refCls = refCls.getSuperclass();
        } while (refCls != null);
        return null;
    }

    public static Object getFieldValue(Object object, String fieldName) throws IllegalAccessException {
        Field field = getField(object, fieldName);
        return field!=null ? field.get(object) : null;
    }

}