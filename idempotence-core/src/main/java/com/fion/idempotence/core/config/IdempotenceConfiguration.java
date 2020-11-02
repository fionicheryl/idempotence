package com.fion.idempotence.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Idempotence的配置
 *
 * @author fion yang
 * @date 2020-11-01 09:30
 */
@Component
public class IdempotenceConfiguration {

    @Autowired
    private Environment env;

    /**
     * cookie中token的名称
     */
    private String cookieTokenName;

    /**
     * header中token的名称
     */
    private String headerTokenName;

    public IdempotenceConfiguration() {
        this.cookieTokenName = env.getProperty("idempotence.token.name.cookie", "token");
        this.headerTokenName = env.getProperty("idempotence.token.name.header", "token");
    }

    public String getCookieTokenName() {
        return cookieTokenName;
    }

    public String getHeaderTokenName() {
        return headerTokenName;
    }

}
