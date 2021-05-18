package com.isnico.dsl.configure;

import java.lang.reflect.Field;
import java.util.Locale;

public class DefaultConfiguration extends Configuration{

    @Override
    public String rewriteTable(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    public String rewriteColumn(Field field) {
        return field.getName();
    }
}
