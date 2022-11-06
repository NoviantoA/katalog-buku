package novianto.anggoro.spring.catalog.aspect;

import lombok.extern.slf4j.Slf4j;
import novianto.anggoro.spring.catalog.dto.BookDetailResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    // pointcut expression
    @Pointcut("execution(* novianto.anggoro.spring.catalog.web.*.*(..))")
    // pointcut signature
    private void restAPI(){}


    @Pointcut("within(novianto.anggoro.spring.catalog.web.*)")
    private void withinPointcutExample(){}

    @Pointcut("args(novianto.anggoro.spring.catalog.dto.PublisherCreateRequestDTO)")
    private void argPointcutExample(){}

    @Pointcut("@args(novianto.anggoro.spring.catalog.annotation.LogThisArg)")
    private void argsAnnotationPoincutExample(){}

    @Pointcut("@annotation(novianto.anggoro.spring.catalog.annotation.LogThisMethod)")
    private void annotationPointcutExample(){}

    // method aspect
    @Before("annotationPointcutExample()"
//            "restAPI() &&"
//            " && argPointcutExample()"
    )
    public void beforeExecutedLogging(){
        log.info("Ini merupakan log dari aspect sebelum method dieksekusi");
    }

    // method aspect
    @After("annotationPointcutExample()")
    public void afterExecutedLogging(){
        log.info("Ini merupakan log dari aspect sesudah method dieksekusi");
    }

    // method aspect
    @AfterReturning("annotationPointcutExample()")
    public void afterReturnExecutedLogging(){
        log.info("Ini merupakan log dari aspect sesudah return method dieksekusi");
    }

    @AfterThrowing("annotationPointcutExample()")
    public void afterThrowExecutedLogging(){
        log.info("Ini merupakan log dari aspect sesudah throw method dieksekusi");
    }

    @Around("restAPI()")
    public Object processingTimeLogging(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        try {
            log.info("start ***************** {}.{} *****************", joinPoint.getTarget().getClass().getName(),  joinPoint.getSignature().getName());
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
        stopWatch.stop();
        log.info("finish ***************** {}.{} ***************** execution time = {}",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
    }
}
