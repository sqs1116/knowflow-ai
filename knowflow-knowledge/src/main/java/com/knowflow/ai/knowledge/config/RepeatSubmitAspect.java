package com.knowflow.ai.knowledge.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepeatSubmitAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Around(
            value = "@annotation(repeatSubmit)",
            argNames = "joinPoint,repeatSubmit"
    )
    public Object around(ProceedingJoinPoint joinPoint,
                         RepeatSubmit repeatSubmit) throws Throwable {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes)
                        RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();

        String userId = getCurrentUserId();

        String uri = request.getRequestURI();

        String params = Arrays.toString(joinPoint.getArgs());

        String paramHash = DigestUtils.md5DigestAsHex(
                params.getBytes(StandardCharsets.UTF_8)
        );

        String key = "repeat_submit:"
                + userId + ":"
                + uri + ":"
                + paramHash;

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(
                        key,
                        "1",
                        repeatSubmit.interval(),
                        TimeUnit.MILLISECONDS
                );

        if (Boolean.FALSE.equals(success)) {
            throw new RuntimeException("请勿重复提交");
        }

        return joinPoint.proceed();
    }

    private String getCurrentUserId() {
        return "10001";
    }
}
