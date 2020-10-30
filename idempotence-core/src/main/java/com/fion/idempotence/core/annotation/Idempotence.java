package com.fion.idempotence.core.annotation;

import com.fion.idempotence.core.enums.ExtractTokenModel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 幂等控制注解
 *
 * @author fion yang
 * @date 2020-10-29 11:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {METHOD})
public @interface Idempotence {

    ExtractTokenModel model() default ExtractTokenModel.COOKIE;

    String tokenInCookie() default "token";

    String tokenInHeader() default "token";
}
