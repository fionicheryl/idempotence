package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.aspect.IdempotenceContext;

/**
 * token提取处理器
 *
 * @author fion yang
 * @date 2020-10-30 10:15
 */
public interface TokenExtractorHandler {

    /**
     * 提取token
     * 提取成功，会将token存放在context中
     *
     * @param context token上下文
     */
    void extract(IdempotenceContext context);

    /**
     * 判断一维数组是否为空
     *
     * @param arr
     * @return
     */
    default boolean isEmpty(Object[] arr) {
       if (null == arr || arr.length == 0) {
           return true;
       }
       return false;
    }

    /**
     * 判断二维数组是否为空
     *
     * @param arr
     * @return
     */
    default boolean isEmpty(Object[][] arr) {
        if (null == arr || arr.length == 0) {
            return true;
        }
        return false;
    }
}
