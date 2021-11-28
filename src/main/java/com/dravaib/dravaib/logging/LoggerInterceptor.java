package com.dravaib.dravaib.logging;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Autowired
    private Logger logger;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.warn("Intercepting request");
        printRequest(request);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
    }

    public void printRequest(HttpServletRequest httpRequest) {
        logger.warn(" \n\n Headers");

        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            logger.warn(headerName + " = " + httpRequest.getHeader(headerName));
        }

        logger.warn("\n\nParameters");

        Enumeration<String> params = httpRequest.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = (String) params.nextElement();
            System.out.println(paramName + " = " + httpRequest.getParameter(paramName));
        }

        logger.warn("\n\n Row data");
        logger.warn(extractPostRequestBody(httpRequest));
    }

    static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }
}
