package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.annotation.Token;
import com.fion.idempotence.core.exception.NoSupportTokenTypeException;
import com.fion.idempotence.core.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * 参数token提取器
 *
 * @author fion yang
 * @date 2020-10-30 10:26
 */
@Component
public class ParamTokenExtractorHandler implements TokenExtractorHandler {

    /**
     * 提取token
     * 提取成功，会将token存放在context中
     *
     * @param context token上下文
     */
    @Override
    public void extract(TokenContext context) {
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
                    Object token = args[i];
                    if (token instanceof String) {
                        context.setToken((String) token);
                        return;
                    }
                    throw new NoSupportTokenTypeException("Token's type must be a string.");
                }
            }
        }
        throw new TokenNotFoundException("The Token annotation was not found.");
    }
}
