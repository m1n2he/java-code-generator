package com.cc.jcg.main;

public class Bean07
        implements NamedBean {
    
    private final String name;
    private Bean02 bean;
    
    public Bean07(String name) {
        super();
        this.name = name;
    }
    
    @Override
    public final String getName() {
        return name;
    }
    
    @Override
    public final synchronized Bean02 getBean() {
        return bean;
    }
    
    public final synchronized void setBean(Bean02 bean) {
        this.bean = bean;
    }
}
