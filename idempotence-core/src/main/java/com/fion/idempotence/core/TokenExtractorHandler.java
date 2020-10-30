package com.fion.idempotence.core;

/**
 * token提取处理器
 *
 * @author fion yang
 * @date 2020-10-30 10:15
 */
public interface TokenExtractorHandler {

    /**
     * 提取token
     *
     * @param args 参数列表，对应于ProceedingJoinPoint获取到的参数列表
     * @return
     */
    String extract(Object[] args);
}
