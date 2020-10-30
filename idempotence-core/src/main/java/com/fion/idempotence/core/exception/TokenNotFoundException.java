package com.fion.idempotence.core.exception;

/**
 * 没有找到token
 *
 * @author fion yang
 * @date 2020-10-30 16:38
 */
public class TokenNotFoundException extends IdempotenceException {

    public TokenNotFoundException(String msg) {
        super(msg);
    }
}
