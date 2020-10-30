package com.fion.idempotence.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 幂等控制切面
 *
 * @author fion yang
 * @date 2020-10-29 14:40
 */
@Aspect
public class IdempotenceAspect {

    @Pointcut("@annotation(com.fion.idempotence.core.annotation.Idempotence)")
    public void idempotence() {}

    @Around("idempotence()")
    public Object process(ProceedingJoinPoint joinPoint) {
        return null;
    }

}
