package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.annotation.Token;
import com.fion.idempotence.core.aspect.IdempotenceContext;
import com.fion.idempotence.core.exception.TokenNotFoundException;
import com.fion.idempotence.core.exception.NoSupportTokenTypeException;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字段token提取器
 *
 * @author fion yang
 * @date 2020-10-30 15:34
 */
@Component
public class FiledTokenExtractorHandler implements TokenExtractorHandler {

    /**
     * 提取token
     * 提取成功，会将token存放在context中
     *
     * @param context token上下文
     */
    @Override
    public void extract(IdempotenceContext context) {
        Object[] args = context.getArgs();
        if (isEmpty(args)) {
            throw new TokenNotFoundException("Methods have no arguments.");
        }
        Annotation[][] paramAnnotations = context.getParamAnnotations();
        if (isEmpty(paramAnnotations)) {
            throw new TokenNotFoundException("The Token annotation was not found.");
        }
        int length = paramAnnotations.length;
        for (int i = 0; i < length; ++i) {
            if (isEmpty(paramAnnotations[i])) {
                continue;
            }
            for (Annotation annotation : paramAnnotations[i]) {
                if (annotation instanceof Token) {
                    context.setToken(getToken((Token) annotation, args[i]));
                    return;
                }
            }
        }
        throw new TokenNotFoundException("The Token annotation was not found.");
    }

    /**
     * 获取token
     *
     * @param token token注解对象
     * @param obj   实际对象
     * @return
     */
    private String getToken(Token token, Object obj) {
        String fieldName = token.fieldName();
        Class clazz = obj.getClass();

        Object tokenValue;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            tokenValue = field.get(obj);
        } catch (Exception e) {
            throw new TokenNotFoundException("Please check whether " + fieldName + " exists.");
        }
        if (tokenValue instanceof String) {
            return (String) tokenValue;
        }
        throw new NoSupportTokenTypeException("Token's type must be a string");
    }
}
