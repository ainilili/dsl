package com.isnico.dsl.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    String value();

    String schema() default "";

    String alias() default "";
}
