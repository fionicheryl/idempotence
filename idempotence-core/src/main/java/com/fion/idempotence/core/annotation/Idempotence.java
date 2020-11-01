package com.fion.idempotence.core.annotation;

import com.fion.idempotence.core.handler.CookieTokenExtractorHandler;
import com.fion.idempotence.core.handler.TokenExtractorHandler;
import com.fion.idempotence.core.repository.DefaultSupportTokenRepository;
import com.fion.idempotence.core.repository.TokenRepositoryAdapter;

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

    /**
     * token存储适配器
     * 默认使用基于内存的存储
     *
     * @return
     */
    Class<? extends TokenRepositoryAdapter> tokenRepositoryAdapter() default DefaultSupportTokenRepository.class;

    String tokenInCookie() default "token";

    String tokenInHeader() default "token";
}
