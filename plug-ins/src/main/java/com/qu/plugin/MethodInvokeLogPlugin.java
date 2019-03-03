package com.qu.plugin;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MethodInvokeLogPlugin implements MethodBeforeAdvice {
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(method.getDeclaringClass().getName() +":" + method.getName());
    }
}
