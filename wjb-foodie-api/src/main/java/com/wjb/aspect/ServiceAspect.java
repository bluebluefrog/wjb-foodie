package com.wjb.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

    //定义logger
    public static final Logger log = LoggerFactory.getLogger(ServiceAspect.class);

    //切面表达式 execution代表所要执行的表达式主体
    @Around("execution(* com.wjb.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录开始执行的方法
        log.info("方法开始执行 {}.{}",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        //记录开始时间
        long begin = System.currentTimeMillis();

        //执行目标service
        Object result = joinPoint.proceed();

        //记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("执行结束耗时:{} 毫秒", takeTime);
        }else if(takeTime > 2000){
            log.warn("执行结束耗时:{} 毫秒", takeTime);
        }else{
            log.info("执行结束耗时:{} 毫秒", takeTime);
        }

        return result;
    }
}
