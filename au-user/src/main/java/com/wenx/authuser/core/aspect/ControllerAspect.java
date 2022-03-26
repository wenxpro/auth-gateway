package com.wenx.authuser.core.aspect;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution (* com.wenx.authuser*..*Controller.*(..))")
    public void sysAccessLog() {
    }

    @Around(value = "sysAccessLog()")
    public Object doArround(ProceedingJoinPoint pjp) throws Throwable {
        long stime = System.currentTimeMillis();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //运行方法
        Object o = pjp.proceed();
        long etime = System.currentTimeMillis();
        assert attributes != null;
        ControllerLog controllerLog = handlerRequest(attributes.getRequest(), pjp, o, (etime - stime) + "ms");
        log.info("[HTTP_>>>]: {}", controllerLog);
        return o;
    }

    ControllerLog handlerRequest(HttpServletRequest req, ProceedingJoinPoint pjp, Object o, String time) {
        ControllerLog controllerLog = new ControllerLog();
        controllerLog.setUrl(req.getRequestURL().toString());
        controllerLog.setMethod(req.getMethod());
        controllerLog.setAction(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        controllerLog.setParams(JSON.toJSONString(getMethodArg(pjp.getArgs())));
        controllerLog.setIp(req.getRemoteAddr());
        controllerLog.setTime(time);
        return controllerLog;
    }


    private Object[] getMethodArg(Object[] args) {
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile || ((args[i] instanceof List) && ((List<?>) args[i]).get(0) instanceof MultipartFile)) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        return arguments;
    }

    @Data
    public static class ControllerLog {
        private String url;
        private String method;
        private String action;
        private String params;
        private String ip;
        private String time;
    }


}
