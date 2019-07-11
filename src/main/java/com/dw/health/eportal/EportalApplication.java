package com.dw.health.eportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dw.health.framework.util.SpringBeanUtils;

import tk.mybatis.spring.annotation.MapperScan;

//@HealthFrameworkApplication
@SpringBootApplication
//@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dw.health.eportal.dao")
@ImportResource(locations = {"classpath:shiro-spring.xml"})
@ComponentScan(basePackages = {"com.dw.health.core", "com.dw.health.eportal"})
public class EportalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EportalApplication.class, args);
       SpringBeanUtils.setContext(context);
    }

}

