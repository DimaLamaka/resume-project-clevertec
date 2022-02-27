package by.lamaka.resume.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Сlass for logging in aop style
 */

@Component
@Slf4j
@Aspect
public class LoggingAspect {
    /**
     * Create pointcut
     */
    @Pointcut("within(by.lamaka.resume..*) " +
            "|| within(by.lamaka.resume.service..*)" +
            "|| within(by.lamaka.resume.repository..*)" +
            "|| within(by.lamaka.resume.controller..*)")
    public void packagePointcut() {
    }

    /**
     * The method works when an exception is thrown and writes to the log
     *
     * @param joinPoint joinPoint
     * @param e         Throwable
     * @see JoinPoint
     * @see Throwable
     */
    @AfterThrowing(pointcut = "packagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getMessage());
    }

    /**
     * The method works before and after calling any method and writes to the log
     *
     * @param joinPoint point where functionality is planned to be introduced
     * @return the object returned by the method
     * @throws Throwable joinPoint.proceed() can throw an exception
     */
    @Around("packagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        Object obj = joinPoint.proceed();

        if (log.isInfoEnabled()) {
            log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), obj);
        }
        if (log.isDebugEnabled()) {
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), obj);
        }

        return obj;

    }
}
