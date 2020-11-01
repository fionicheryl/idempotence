package com.fion.idempotence.core.repository;

import com.fion.idempotence.core.exception.BeanNotFoundException;
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
public class TokenRepositoryAdapterFactory {

    /**
     * token存储适配器
     */
    private final Map<Class<? extends TokenRepositoryAdapter>, TokenRepositoryAdapter> adapterFactory;

    /**
     * 工厂构造，注入所有token存储适配器
     *
     * @param adapters token存储适配器
     */
    @Autowired
    public TokenRepositoryAdapterFactory(List<TokenRepositoryAdapter> adapters) {
        if (CollectionUtils.isEmpty(adapters)) {
            this.adapterFactory = new HashMap<>();
        } else {
            this.adapterFactory = new HashMap<>(adapters.size());
            for (TokenRepositoryAdapter adapter : adapters) {
                this.adapterFactory.put(adapter.getClass(), adapter);
            }
        }
    }

    /**
     * 获得token存储适配器实例
     *
     * @param adapterClazz token存储适配器类
     * @return
     */
    public TokenRepositoryAdapter getInstance(Class<? extends TokenRepositoryAdapter> adapterClazz) {
        // 获取提取器处理器
        TokenRepositoryAdapter adapter = adapterFactory.get(adapterClazz);
        if (null == adapter) {
            throw new BeanNotFoundException("No matching bean found for " + adapterClazz.getName() + ".");
        }
        return adapter;
    }

}
