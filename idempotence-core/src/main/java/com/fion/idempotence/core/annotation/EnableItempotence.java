package com.fion.idempotence.core.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 *
 * @author fion yang
 * @date 2020-11-12 18:32
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {TYPE})
@Import()
public @interface EnableItempotence {
}
