package com.fion.idempotence.core.annotation;

import com.fion.idempotence.core.handler.CookieTokenExtractorHandler;
import com.fion.idempotence.core.handler.TokenExtractorHandler;

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

    /**
     * token提取器处理器
     * 默认从cookie中提取
     *
     * @return
     */
    Class<? extends TokenExtractorHandler> tokenExtractorHandler() default CookieTokenExtractorHandler.class;

    String tokenInCookie() default "token";

    String tokenInHeader() default "token";
}
