package com.isnico.dsl.tools;

import com.isnico.dsl.exception.DslException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassUtil {

    private static final Map<Class<?>, Field[]> cachedClassFields = new ConcurrentHashMap<>();

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

    public static Field[] getCachedFields(Class<?> clazz){
        Field[] fields = cachedClassFields.get(clazz);
        if (fields != null){
            return fields;
        }
        fields = clazz.getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
        }
        return fields;
    }

    public static boolean isPrimitive(Class<?> clazz){
        return clazz.isPrimitive()
                || isAssignableFrom(clazz,
                Integer.class,
                Byte.class,
                Character.class,
                Short.class,
                Long.class,
                Float.class,
                Double.class,
                Boolean.class,
                Number.class,
                String.class);
    }

    public static boolean isNumber(Class<?> clazz){
        return isAssignableFrom(
                clazz,
                Byte.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,
                byte.class,
                short.class,
                int.class,
                long.class,
                float.class,
                double.class,
                Number.class);
    }

    public static boolean isAssignableFrom(Class<?> target, Class<?>... classes){
        for (Class<?> clazz: classes){
            if (target.isAssignableFrom(clazz)){
                return true;
            }
        }
        return false;
    }

    public static void setFieldValue(Field field, Object target, Object value) throws IllegalAccessException {
        if (value != null){
            if (isNumber(field.getType())){
                if (value instanceof Number){
                    if (isAssignableFrom(field.getType(), byte.class, Byte.class)){
                        field.set(target, ((Number) value).byteValue());
                    }else if (isAssignableFrom(field.getType(), short.class, Short.class)){
                        field.set(target, ((Number) value).shortValue());
                    }else if (isAssignableFrom(field.getType(), int.class, Integer.class)){
                        field.set(target, ((Number) value).intValue());
                    }else if (isAssignableFrom(field.getType(), long.class, Long.class)){
                        field.set(target, ((Number) value).longValue());
                    }else if (isAssignableFrom(field.getType(), float.class, Float.class)){
                        field.set(target, ((Number) value).floatValue());
                    }else if (isAssignableFrom(field.getType(), double.class, Double.class)){
                        field.set(target, ((Number) value).doubleValue());
                    }else if (isAssignableFrom(field.getType(), BigDecimal.class)){
                        field.set(target, BigDecimal.valueOf(((Number) value).doubleValue()));
                    }else if (isAssignableFrom(field.getType(), BigInteger.class)){
                        field.set(target, BigInteger.valueOf(((Number) value).longValue()));
                    }
                }
            }else if (isAssignableFrom(field.getType(), boolean.class, Boolean.class)){
                if (value instanceof Number){
                    field.set(target, ((Number) value).intValue() == 1);
                }else{
                    field.set(target, Boolean.valueOf(value.toString()));
                }
            }else if (isAssignableFrom(field.getType(), char.class, Character.class)){
                field.set(target, value.toString().charAt(0));
            }else{
                field.set(target, value.toString());
            }
        }
    }

    public static void main(String[] args) {
        int a = 1;
        Integer b = 1;
        Object c = a;
        System.out.println(c.getClass());
        System.out.println(int.class.isAssignableFrom(Integer.class));
        System.out.println(b.getClass().isAssignableFrom(Integer.class));
    }
}
