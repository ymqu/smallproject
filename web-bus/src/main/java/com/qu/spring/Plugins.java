package com.qu.spring;

import java.io.Serializable;
import java.util.List;

public class Plugins implements Serializable {
    private String name;
    private List<PluginConfig> configs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PluginConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<PluginConfig> configs) {
        this.configs = configs;
    }
}
