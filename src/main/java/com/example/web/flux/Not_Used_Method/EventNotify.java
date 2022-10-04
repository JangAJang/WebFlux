package com.example.web.flux.Not_Used_Method;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventNotify {

    private List<String> event = new ArrayList<>();
    private boolean change = false;

    public void addContent(String data){
        event.add(data);
        change = true;
    }

    public List<String> getEvent() {
        return event;
    }

    public boolean isChanged() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }
}
