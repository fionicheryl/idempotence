package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.aspect.IdempotenceContext;
import com.fion.idempotence.core.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Cookie中提取token
 *
 * @author fion yang
 * @date 2020-10-30 16:54
 */
@Component
public class CookieTokenExtractorHandler implements TokenExtractorHandler{

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
        String tokenInCookie = context.getIdempotence().tokenInCookie();
        Cookie[] cookies = request.getCookies();
        if (isEmpty(cookies)) {
            throw new TokenNotFoundException("Cookie info is empty.");
        }
        for (Cookie cookie : cookies) {
            if (tokenInCookie.equals(cookie.getName())) {
                context.setToken(cookie.getValue());
                return;
            }
        }
        throw new TokenNotFoundException("There is no " + tokenInCookie + " in cookies.");
    }
}
