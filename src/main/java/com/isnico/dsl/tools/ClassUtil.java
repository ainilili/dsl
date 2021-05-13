package com.isnico.dsl.tools;

import com.isnico.dsl.exception.DslException;

import java.lang.reflect.Field;
import java.util.Locale;

public class ClassUtil {

    public static Class<?> forClass(String className){
        try {
            return Class.forName(className.replaceAll("/", "."));
        } catch (ClassNotFoundException e) {
            throw new DslException(e);
        }
    }

    public static Field parseFieldWithGetter(Class<?> clazz, String method) {
        String fieldName = "";
        if (method.startsWith("is")){
            fieldName = method.substring(2);
        }else if (method.startsWith("get")){
            fieldName = method.substring(3);
        }
        fieldName = fieldName.toLowerCase(Locale.ROOT);
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new DslException(e);
        }
    }
}
