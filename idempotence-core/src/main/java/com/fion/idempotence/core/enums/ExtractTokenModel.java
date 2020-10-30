package com.fion.idempotence.core.enums;

import com.fion.idempotence.core.annotation.Idempotence;
import com.fion.idempotence.core.annotation.Token;

/**
 * 提取token的方式枚举
 *
 * @author fion yang
 * @date 2020-10-30 17:00
 */
public enum ExtractTokenModel {

    /**
     * 从方法参数中根据token注解 {@link Token} 直接提取
     */
    PARAM,

    /**
     * 1. 从方法参数中根据token注解 {@link Token} 提取对象
     * 2. 根据token注解的fieldName获取token
     */
    FIELD,

    /**
     * 特用于web方式
     * 从cookie信息中提取token，cookie中的token名称取Idempotence注解 {@link Idempotence} 的tokenInCookie
     */
    COOKIE,

    /**
     * 特用于web方式
     * 从请求头信息中提取token，header中的token名称取Idempotence注解 {@link Idempotence} 的tokenInHeader
     */
    HEADER

    ;
}
