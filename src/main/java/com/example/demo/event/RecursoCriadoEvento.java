package com.example.demo.event;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvento extends ApplicationEvent {

    private static final long serialVersionUID = 1072393690628090425L;
    private HttpServletResponse response;
    private Long id;

    public RecursoCriadoEvento(Object source,HttpServletResponse response,Long id) {
        super(source);
        this.id = id;
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse reponse) {
        this.response = reponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}