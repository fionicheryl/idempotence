package com.fion.idempotence.core.exception;

/**
 * 未找到bean异常
 *
 * @author fion yang
 * @date 2020-11-01 14:30
 */
public class BeanNotFoundException extends IdempotenceException {
    public BeanNotFoundException(String msg) {
        super(msg);
    }
}
