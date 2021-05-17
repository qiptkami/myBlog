package com.yiqiandewo.aspect;

import com.yiqiandewo.util.IpInfoUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.yiqiandewo.controller.*.*(..))")
    public void log() {}

    @Around("log()")
    public Object around(ProceedingJoinPoint pcj) {
        Object rtVal = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();

        //String ip = request.getRemoteAddr(); //可能拿不到真实ip
        String ip = IpInfoUtils.getIpAddr(request);

        String classMethod = pcj.getSignature().getDeclaringTypeName() + "." + pcj.getSignature().getName();
        Object[] args = pcj.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}" ,requestLog);

        try {
            rtVal = pcj.proceed();  //明确切入点方法的调用
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            logger.info("Result : {}", rtVal);
        }

        return rtVal;
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
