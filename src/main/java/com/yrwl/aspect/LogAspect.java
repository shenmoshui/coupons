package com.yrwl.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author  shentao
 * @date  2019-04-30
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<String> methodName = new ThreadLocal<>();

    @Pointcut("execution(public * com.yrwl.api.controller..*.*(..))")
    public void log(){
    }


    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        startTime.set(System.currentTimeMillis());
        methodName.set(joinPoint.getSignature().getName());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        if(joinPoint.getArgs().length == 0){
            log.info(joinPoint.getSignature().getName() + "_REQUEST : NO PARAMS");
        }
        else{
            log.info(joinPoint.getSignature().getName() + "_REQUEST : " + Arrays.toString(joinPoint.getArgs()));
        }

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info(methodName.get() + "_RESPONSE : " + JSONObject.toJSONString(ret));
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "ms");
        startTime.remove();//用完之后记得清除，不然可能导致内存泄露;
        methodName.remove();
    }
}
