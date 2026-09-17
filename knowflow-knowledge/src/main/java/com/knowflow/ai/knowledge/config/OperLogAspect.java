package com.knowflow.ai.knowledge.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperLogAspect {



    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint joinPoint,
                         OperLog operLog) throws Throwable {

        // ① 获取注解里面的值
        String module = operLog.module();
        String operation = operLog.operation();

        System.out.println("模块：" + module);
        System.out.println("操作：" + operation);

        // ② 执行原来的业务方法
        Object result = joinPoint.proceed();

        // ③ 方法执行完成之后
        System.out.println("操作完成");

        return result;
    }
}
