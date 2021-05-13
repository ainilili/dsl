package com.isnico.dsl.configure;

import java.lang.reflect.Field;

public abstract class Configuration {

    public abstract String rewriteTable(Class<?> clazz);

    public abstract String rewriteColumn(Field field);
}
