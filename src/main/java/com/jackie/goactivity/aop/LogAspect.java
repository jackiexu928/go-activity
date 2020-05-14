package com.jackie.goactivity.aop;

import com.jackie.goactivity.process.CommonInterface;
import com.jackie.goactivity.process.Context;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import util.LogUtil;


/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger("Service");

    @Pointcut("execution(public * com.jackie.goactivity.service.*.*(..))")
    public void LogAspect(){}

    @Around("LogAspect()")
    public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object targetObject = joinPoint.getTarget();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();

        Long start = System.currentTimeMillis();
        Long end = 0L;

        Context context = new Context();
        if (targetObject instanceof CommonInterface){
            CommonInterface commonInterface = (CommonInterface) targetObject;
            if (params == null){
                context.setParam(null);
            } else {
                context.setParam(params[0]);
            }
            try {
                commonInterface.onStarted(context);
                Object result = joinPoint.proceed();
                context = (Context) result;
                commonInterface.onSuccess(context);
            } catch (Exception e){
                commonInterface.onError(context, e);
                LogUtil.error(logger, className + "." + methodName, context.getParam(), e);
            } finally {
                context.setClassName(className);
                context.setMethodName(methodName);
                commonInterface.onEnd(context);
            }
            end = System.currentTimeMillis();
            LogUtil.info(logger, className + "." + methodName, context.getParam(),
                    "耗时：" + (end - start) + "ms");
            return context;
        } else {
            Object o = joinPoint.proceed();
            end = System.currentTimeMillis();
            LogUtil.info(logger, className + "." + methodName, joinPoint.getArgs(),
                    "耗时：" + (end - start) + "ms");
            return o;
        }
    }
}
