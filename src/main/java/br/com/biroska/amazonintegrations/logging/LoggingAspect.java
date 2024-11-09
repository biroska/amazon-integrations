package br.com.biroska.amazonintegrations.logging;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    // Implementar parametro na interface para parametrizar a captura por essa AOP
    @Around("@annotation(LogMethod)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Thread currentThread = Thread.currentThread();
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.info("[Thread] {} [Execution time] {} executed in {} ms", currentThread.getName(), joinPoint.getSignature(), executionTime);
        return proceed;
    }

    @Before("@annotation(LogMethod)")
    public void logMethodBeginingExecution(JoinPoint joinPoint) {

        buildMethodNameToLog(buildMethodNameToLog( joinPoint.getSignature().toString() ) );

        Thread currentThread = Thread.currentThread();
        logger.info( "[Thread] {} [Starting]: {} ", currentThread.getName(), buildMethodNameToLog( joinPoint.getSignature().toString() ) );
    }

    @After("@annotation(LogMethod)")
    public void logMethodEndingExecution(JoinPoint joinPoint) {

        Thread currentThread = Thread.currentThread();
        logger.info( "[Thread] {} [Ending]: {} ", currentThread.getName(), buildMethodNameToLog( joinPoint.getSignature().toString() ) );
    }
    
    private String buildMethodNameToLog(String signature) {

        if (StringUtils.isBlank(signature)) { return signature; }

        String partName = "Not Found";

        try {
            String[] splitted = StringUtils.split(signature);

            if (ArrayUtils.isNotEmpty(splitted)) {

                String returnType = splitted[0];
                String qualifiedMethod = splitted[1];

                String[] tokensFromMethod = StringUtils.splitPreserveAllTokens(qualifiedMethod, ".");

                if (ArrayUtils.isNotEmpty(tokensFromMethod)) {

                    int length = tokensFromMethod.length;

                    String method = tokensFromMethod[length - 1];
                    String className = tokensFromMethod[length - 2];

                    partName = returnType + " " + className + "." + method;
                }
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao construir o nome do metodo no aspecto. {}", e.getMessage(), e);
        }

        return partName;

    }
}
