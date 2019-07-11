package com.dw.health.core.security.shiro;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dw.health.core.config.DwTags;
import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;

@Component
public class TagConfig implements InitializingBean {
    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
        configuration.setSharedVariable("dw", new DwTags());
    }
}
