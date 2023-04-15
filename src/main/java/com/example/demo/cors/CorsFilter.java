package com.example.demo.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter
{
    //String frontend_url = "http://localhost:63342";
    //String url = "http://localhost:4200";
    String frontend_url = "*";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletRequest request   = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        response.setHeader("Access-Control-Allow-Origin", frontend_url);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, Origen");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, POST, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setStatus(HttpServletResponse.SC_OK);

        if ("OPTIONS".equals(request.getMethod())) {
            //&& url.equals(request.getHeader("Origen")
            //response.setHeader("Access-Control-Max-Age", "3600");
            //response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                chain.doFilter(req, res);
            } catch (IOException e) {
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

}