package com.example.web.flux.Not_Used_Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig{

    @Autowired
    private EventNotify eventNotify;

    @Bean
    public FilterRegistrationBean<Filter> addFilter(){
        System.out.println("장 필터 등록됨");
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new JangFilter(eventNotify));
        bean.addUrlPatterns("/sse");
        return bean;
    }
    @Bean
    public FilterRegistrationBean<Filter> addFilter2(){
        System.out.println("종 필터 등록됨");
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new JongFilter(eventNotify));
        bean.addUrlPatterns("/add");
        return bean;
    }
}
