package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.aspect.IdempotenceContext;
import com.fion.idempotence.core.config.IdempotenceConfiguration;
import com.fion.idempotence.core.exception.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IdempotenceConfiguration configuration;

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
        String tokenInHeader = configuration.getHeaderTokenName();
        String token = request.getHeader(tokenInHeader);
        if (null == token || "".equals(token)) {
            throw new TokenNotFoundException("There is no " + tokenInHeader + " in headers.");
        }
        context.setToken(token);
    }
}
