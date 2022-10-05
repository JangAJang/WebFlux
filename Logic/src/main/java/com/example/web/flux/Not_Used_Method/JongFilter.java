package com.example.web.flux.Not_Used_Method;

import javax.servlet.*;
import java.io.IOException;

public class JongFilter implements Filter {

    private EventNotify eventNotify;

    public JongFilter(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("종 필터 실행");
        eventNotify.addContent("새로운 데이터 추가");
    }
}
