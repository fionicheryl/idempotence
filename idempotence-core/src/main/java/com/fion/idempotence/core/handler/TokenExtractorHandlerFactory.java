package com.fion.idempotence.core.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

    private final Map<Class<? extends TokenExtractorHandler>, TokenExtractorHandler> handlersFactory;

    /**
     * 工厂构造，注入所有token提取器处理器
     *
     * @param handlers token提取器处理器
     */
    @Autowired
    public TokenExtractorHandlerFactory(List<TokenExtractorHandler> handlers) {
        if (CollectionUtils.isEmpty(handlers)) {
            this.handlersFactory = new HashMap<>();
        } else {
            this.handlersFactory = new HashMap<>(handlers.size());
            for (TokenExtractorHandler handler : handlers) {
                this.handlersFactory.put(handler.getClass(), handler);
            }
        }
    }

    /**
     * 获得token提取器处理器实例
     *
     * @param handlerClazz token提取器处理器类
     * @return
     */
    public TokenExtractorHandler getInstance(Class<? extends TokenExtractorHandler> handlerClazz) {
        return handlersFactory.get(handlerClazz);
    }

}
