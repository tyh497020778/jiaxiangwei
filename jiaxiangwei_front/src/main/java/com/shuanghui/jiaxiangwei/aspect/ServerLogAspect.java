package com.shuanghui.jiaxiangwei.aspect;

import com.alibaba.fastjson.JSONObject;
import com.shuanghui.jiaxiangwei.dto.JxwServerLogDto;
import com.shuanghui.jiaxiangwei.mapper.JxwServerLogMapper;
import com.shuanghui.jiaxiangwei.service.IJxwServerLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务日志的切面
 */
@Aspect
@Component
public class ServerLogAspect {
    @Autowired
    private IJxwServerLogService jxwServerLogService;

    /**
     这里我们使用注解的形式 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method 切点表达式:
     * execution(...)
     */
    @Pointcut("@annotation(com.shuanghui.jiaxiangwei.aspect.ServerLog)")
    public void logPointCut(){

    }

    /**
     * 环绕通知 @Around ， 当然也可以使用 @Before (前置通知) @After (后置通知)
     * @return
     */
    @Around("logPointCut()")
    private void saveLog(ProceedingJoinPoint joinPoint) throws Throwable{
        JxwServerLogDto serverLog = new JxwServerLogDto();
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(JSONObject.toJSONString(o));
            }
            serverLog.setParams(list.toString());

        } catch (Exception e) {
        }
        Object result = joinPoint.proceed(args);
        serverLog.setResult(JSONObject.toJSONString(result));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Date date = new Date();
        serverLog.setCreationDate(date);
        serverLog.setLastDate(date);
        serverLog.setCreateBy(-1L);
        serverLog.setLastUpdateBy(-1L);
        // 请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        serverLog.setClassPath(className);
        serverLog.setMethodName(methodName);
        jxwServerLogService.insert(serverLog);
    }
}
