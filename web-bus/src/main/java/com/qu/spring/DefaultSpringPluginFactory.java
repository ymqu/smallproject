package com.qu.spring;

import com.alibaba.fastjson.JSON;
import org.aopalliance.aop.Advice;
import org.aspectj.util.FileUtil;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class DefaultSpringPluginFactory implements ApplicationContextAware {

    private static final String configPath = "E:\\config\\plugins.config";
    private ApplicationContext applicationContext;
    private Map<String, PluginConfig> configs = new HashMap<String, PluginConfig>();
    private Map<String, Advice> adviceCache = new HashMap<String, Advice>();

    /**
     * active plug in
     * @param pluginId
     */
    public void activePlugin(String pluginId) throws Exception {

        PluginConfig config = configs.get(pluginId);
        for (String name: applicationContext.getBeanDefinitionNames()
             ) {
            Object bean = applicationContext.getBean(name);
            if(bean == this) continue;
            if(!(bean instanceof Advised)) continue;

            if(findAdvice(config.getClassName(), (Advised) bean) != null) continue;

            Advice advice= buildAdvice(config);
            ((Advised) bean).addAdvice(advice);
        }

    }
    public void disablePlugin(String PluginId){
        PluginConfig config = configs.get(PluginId);

        for (String name: applicationContext.getBeanDefinitionNames()
             ) {
            Object bean = applicationContext.getBean(name);
            if(bean != this) continue;
            if(bean instanceof Advised) continue;
            if(findAdvice(config.getClassName(), (Advised) bean) != null) ((Advised) bean).removeAdvice(findAdvice(config.getClassName(), (Advised) bean));
        }

    }

    public Advice buildAdvice(PluginConfig config) throws Exception {
        if(adviceCache.containsKey(config.getClassName())) return adviceCache.get(config.getClassName());

        URL targetURL = new URL(config.getJarRemoteUrl());

        URLClassLoader loader = (URLClassLoader) getClass().getClassLoader();

        boolean isLoader = false;
        for (URL url: loader.getURLs()
             ) {
            if(url.equals(targetURL)){
                isLoader = true;
                break;
            }
        }
        if(!isLoader){
            Method url = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            url.setAccessible(true);
            url.invoke(loader, targetURL);
        }

        Class<?> aClass = loader.loadClass(config.getClassName());
        adviceCache.put(aClass.getName(), (Advice) aClass.newInstance());
        return adviceCache.get(aClass.getName());
    }


    private Advice findAdvice(String className, Advised advised){
        for (Advisor a : advised.getAdvisors()
             ) {
            if(a.getAdvice().getClass().getName().equals(className)){
                return a.getAdvice();
            }
        }
        return  null;
    }


    public void flushConfigs() throws IOException {
        File configFile = new File(configPath);
        String configJson = FileUtil.readAsString(configFile);
        Plugins pluginConfigs = JSON.parseObject(configJson, Plugins.class);

    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }


}
