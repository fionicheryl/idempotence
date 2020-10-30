package com.fion.idempotence.core.handler;

import com.fion.idempotence.core.annotation.Idempotence;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * token 上下文
 *
 * @author fion yang
 * @date 2020-10-30 14:27
 */
public class TokenContext {

    /**
     * 幂等注解
     */
    private Idempotence idempotence;

    /**
     * 方法参数列表
     */
    private Object[] args;

    /**
     * 所有参数注解
     */
    private Annotation[][] paramAnnotations;

    /**
     * request对象
     */
    private HttpServletRequest request;

    /**
     * token
     */
    private String token;

    public Idempotence getIdempotence() {
        return idempotence;
    }

    public void setIdempotence(Idempotence idempotence) {
        this.idempotence = idempotence;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Annotation[][] getParamAnnotations() {
        return paramAnnotations;
    }

    public void setParamAnnotations(Annotation[][] paramAnnotations) {
        this.paramAnnotations = paramAnnotations;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
