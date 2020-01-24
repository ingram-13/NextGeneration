package personal.personalblogreturn.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import personal.personalblogreturn.pojo.RequestLog;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ljh
 * AOP横切实现日志
 */


@Aspect
@Component
public class LogAspect {

    @Autowired
    private RequestLog requestLog;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* personal.personalblogreturn.controller.*.*(..))")
    public void log(){

    }

    /*获得URL,IP,method,args*/
    @Before("log()")
    public void bLog(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        /*获取类名+方法名*/
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        requestLog.setIp(ip);
        requestLog.setUrl(url);
        requestLog.setClassMethod(classMethod);
        requestLog.setArgs(args);
        logger.info("===>Request: {}",requestLog);
    }

    @After("log()")
    public void aLog(){
        /*logger.info("----doAfter----");*/
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void aReturn(Object result){
        logger.info("===>Result: {}", result);

    }


}
