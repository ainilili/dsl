package com.isnico.dsl.tools;

import com.isnico.dsl.exception.DslException;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class LambdaUtil {

    public static <T, R> SerializedLambda resolve(DFunction<T, R> function){
        try {
            Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object o = writeReplace.invoke(function);
            if (o instanceof SerializedLambda){
                return (SerializedLambda) o;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new DslException(e);
        }
        throw new DslException("serialized lambda resolve failure!");
    }
}
