package com.fion.idempotence.core.repository;

import com.fion.idempotence.core.TokenExtractorHandler;

/**
 * 参数token提取器
 *
 * @author fion yang
 * @date 2020-10-30 10:26
 */
public class ParamTokenExtractorHandler implements TokenExtractorHandler {

    /**
     * 提取token
     *
     * @param args 参数列表，对应于ProceedingJoinPoint获取到的参数列表
     * @return
     */
    @Override
    public String extract(Object[] args) {
        return null;
    }
}
