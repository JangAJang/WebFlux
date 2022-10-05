package com.example.web.flux.Not_Used_Method;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JangFilter implements Filter {

    private EventNotify eventNotify;

    public JangFilter(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("장 필터 실행됨");
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        //text/plane이면 한 번 응답을 하고 1초씩 기다리는게 아니라, 1초씩 반복해서 10번 다 기다리고 한번에 응답을 내보낸다.
        //event-stream으로 실행했을 시 1초씩 딜레이를 두어 응답이 한번씩 이루어지게 하며, 이를 다 실행하고 나서 필터가 실행됐다는 로그를 볼 수 있다.
        //event-stream은 딜레이된 응답들이 완료되면 브라우저 입장에서 리스폰스가 끝났다는 것을 의미한다.
        servletResponse.setContentType("text/event-stream; charset=utf-8");
        PrintWriter out = servletResponse.getWriter();

        //webFlux
        for(int i=0; i<10; i++){
            out.println("응답 : " + (i+1));
            out.flush();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //무한 반복을 통해서 서버가 꺼지지 않게 계속 돌려둘 수 있다.
        while(true){
            try {
                if(eventNotify.isChanged()){
                    int lastIndex = eventNotify.getEvent().size()-1;
                    out.println("응답 : " + eventNotify.getEvent().get(lastIndex));
                    eventNotify.setChange(false);
                    out.flush();
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
