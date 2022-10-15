package com.springsamples.heroesapi.config.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class LoggingExecutionTimeAspect {

    private static StopWatch stopWatch = new StopWatch();

    @Around("@annotation(com.springsamples.heroesapi.config.aspects.LogExecutionTime)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        stopWatch.start();
        Object proceed = point.proceed();
        stopWatch.stop();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Method ").append(point.getSignature().getName())
                .append(" from Class ").append(point.getSignature().getDeclaringType())
                .append(" took ").append(stopWatch.getTime(TimeUnit.MILLISECONDS))
                .append(" ms");
        stopWatch.reset();
        log.info(stringBuilder.toString());
        return proceed;
    }

}
