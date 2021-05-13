package com.isnico.dsl.tools;

import java.io.Serializable;

@FunctionalInterface
public interface DFunction<T, R> extends java.util.function.Function<T, R>, Serializable {

}
