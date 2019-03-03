package com.qu.plugin;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class CountTimesPlugin implements MethodBeforeAdvice {
    private int count;

    public void before(Method method, Object[] objects, Object o) throws Throwable {

        count(method);
        System.out.printf(method.getName() + ":" + count);
    }

    private void count(Method method) {
        count++;
    }
}
