package com.fion.idempotence.core.repository;

/**
 * token仓库适配器
 *
 * @author fion yang
 * @date 2020-10-29 15:12
 */
public interface TokenRepositoryAdapter {

    /**
     * 设置token
     *
     * @param token token
     * @param ttl   生存时间
     */
    boolean set(String token, long ttl);

    /**
     * 获取token
     *
     * @param token token
     * @return
     */
    Object get(String token);

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    Object remove(String token);

}
