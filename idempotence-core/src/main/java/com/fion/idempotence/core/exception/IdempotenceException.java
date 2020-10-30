package com.fion.idempotence.core.exception;

/**
 * 幂等处理异常
 *
 * @author fion yang
 * @date 2020-10-30 15:27
 */
public class IdempotenceException extends RuntimeException {

    public IdempotenceException(String msg) {
        super(msg);
    }
}
