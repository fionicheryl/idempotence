package com.fion.idempotence.core.aspect;

import com.fion.idempotence.core.annotation.Idempotence;
import com.fion.idempotence.core.exception.IdempotenceException;
import com.fion.idempotence.core.handler.TokenExtractorHandler;
import com.fion.idempotence.core.handler.TokenExtractorHandlerFactory;
import com.fion.idempotence.core.repository.TokenRepositoryAdapter;
import com.fion.idempotence.core.repository.TokenRepositoryAdapterFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 幂等控制切面
 *
 * @author fion yang
 * @date 2020-10-29 14:40
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class IdempotenceAspect {

    /**
     * 日志打印对象
     */
    private final static Logger log = Logger.getLogger(IdempotenceAspect.class.getName());

    /**
     * token提取器处理器工厂
     */
    @Autowired
    private TokenExtractorHandlerFactory tokenExtractorHandlerFactory;

    /**
     * token存储适配器
     */
    @Autowired
    private TokenRepositoryAdapterFactory tokenRepositoryAdapterFactory;

    @Pointcut("@annotation(com.fion.idempotence.core.annotation.Idempotence)")
    public void idempotence() {}

    @Before("idempotence()")
    public void before(JoinPoint joinPoint) {
        // 构建幂等上下文对象
        IdempotenceContext context = buildContext(joinPoint);
        // 获取token提取器处理器
        TokenExtractorHandler handler = tokenExtractorHandlerFactory.getInstance(context.getIdempotence().tokenExtractorHandler());
        // 提取token，token存放于上下文中
        handler.extract(context);
        // 获取token存储适配器
        TokenRepositoryAdapter adapter = tokenRepositoryAdapterFactory.getInstance(context.getIdempotence().tokenRepositoryAdapter());
        // 判断幂等逻辑
        Object value = adapter.get(context.getToken());
        if (null == value) {
            log.info("[Idempotence Check] token does not exist, token = " + context.getToken());
            // token不存在，表明token伪造或token已被使用
            throw new IdempotenceException("Token does not exist, please apply for token.");
        }
        if (!value.equals(adapter.remove(context.getToken()))) {
            log.info("[Idempotence Check] token has been removed, token = " + context.getToken());
            // 删除的值与获取的不相等，表明token已被处理，幂等
            throw new IdempotenceException("The system is processing, please try again later.");
        }
    }

    /**
     * 尝试获取HttpServletRequest对象
     * 非web环境下可能获取失败
     *
     * @return HttpServletRequest对象
     */
    private HttpServletRequest tryGetRequest() {
        HttpServletRequest request = null;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            request = servletRequestAttributes.getRequest();
        } catch (Exception e) {
            log.log(Level.WARNING, "Failed to get request", e);
        }
        return request;
    }

    /**
     * 构建幂等上下文环境
     *
     * @param joinPoint
     * @return
     */
    private IdempotenceContext buildContext(JoinPoint joinPoint) {
        // 获取参数
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取Idempotence注解
        Idempotence idempotence = method.getAnnotation(Idempotence.class);
        // 获取所有参数注解
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        // 获取HttpServletRequest对象
        HttpServletRequest request = tryGetRequest();
        // 构建IdempotenceContext
        IdempotenceContext context = new IdempotenceContext();
        context.setArgs(args);
        context.setIdempotence(idempotence);
        context.setParamAnnotations(paramAnnotations);
        context.setRequest(request);
        return context;
    }
}
