package com.fion.idempotence.core.exception;

/**
 * 不支持的token类型异常
 *
 * @author fion yang
 * @date 2020-10-30 15:30
 */
public class NoSupportTokenTypeException extends IdempotenceException {

    public NoSupportTokenTypeException(String msg) {
        super(msg);
    }
}
