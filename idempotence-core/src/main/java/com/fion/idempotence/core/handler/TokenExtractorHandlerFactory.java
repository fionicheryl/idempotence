package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.enums.ExtractTokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token提取器工厂
 *
 * @author fion yang
 * @date 2020-10-30 10:08
 */
@Component
public class TokenExtractorHandlerFactory {

    /*private final Map<ExtractTokenModel, TokenExtractorHandler> handlers;

    @Autowired
    public TokenExtractorHandlerFactory(List<TokenExtractorHandler> handlers) {
        handlers = new HashMap<>();
    }*/

}
