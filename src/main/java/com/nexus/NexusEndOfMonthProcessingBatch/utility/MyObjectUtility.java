package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Slf4j
public class MyObjectUtility {

    public static Field getField(Object object,String fieldName) {
        if(object==null) return null;
        Class<?> refCls = object.getClass();
        if(StringUtils.isBlank(fieldName)) return null;
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