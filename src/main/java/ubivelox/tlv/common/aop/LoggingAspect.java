package ubivelox.tlv.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* ubivelox.tlv.controller..*(..))")
    public Object logException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            StackTraceElement topElement = stackTrace[0];

            String errorMessage = String.format("Error occurred in %s.%s at line %d: %s",
                    topElement.getClassName(),
                    topElement.getMethodName(),
                    topElement.getLineNumber(),
                    e.getMessage());

            log.error(errorMessage);
            throw e;
        }
        return result;
    }
}