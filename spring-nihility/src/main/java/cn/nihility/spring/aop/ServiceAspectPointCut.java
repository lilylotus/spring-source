package cn.nihility.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspectPointCut {

    private static final Logger log = LoggerFactory.getLogger(ServiceAspectPointCut.class);

    @Pointcut("execution(* cn.nihility.spring.aop.AspectService.multiplication(int,int))")
    public void multiPointcut() {
    }

    @Around("multiPointcut()")
    public Object multiplicationAroundAspect(ProceedingJoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("multiplication Around proceed [{}] error [{}]", methodName, throwable);
        }
        log.info("multiplication Around proceed [{}] result [{}]", methodName, result);
        return result;
    }

}
