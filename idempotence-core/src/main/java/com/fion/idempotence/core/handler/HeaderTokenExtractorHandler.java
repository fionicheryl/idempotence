package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.aspect.IdempotenceContext;
import com.fion.idempotence.core.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求头中提取token
 *
 * @author fion yang
 * @date 2020-10-30 17:45
 */
@Component
public class HeaderTokenExtractorHandler implements TokenExtractorHandler {

    /**
     * 提取token
     * 提取成功，会将token存放在context中
     *
     * @param context token上下文
     */
    @Override
    public void extract(IdempotenceContext context) {
        HttpServletRequest request = context.getRequest();
        if (null == request) {
            throw new TokenNotFoundException("There is no object of HttpServletRequest in the argument list.");
        }
        String tokenInHeader = context.getIdempotence().tokenInHeader();
        String token = request.getHeader(tokenInHeader);
        if (null == token || "".equals(token)) {
            throw new TokenNotFoundException("There is no " + tokenInHeader + " in headers.");
        }
        context.setToken(token);
    }
}
