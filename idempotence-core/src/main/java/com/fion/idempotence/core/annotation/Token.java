package com.fion.idempotence.core.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

/**
 * token标识注解
 *
 * @author fion yang
 * @date 2020-10-29 15:07
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {PARAMETER})
public @interface Token {

    /**
     * 字段名
     *
     * @return
     */
    @AliasFor("fieldName")
    String value() default "token";

    /**
     * 字段名
     *
     * @return
     */
    @AliasFor("value")
    String fieldName() default "token";
}
