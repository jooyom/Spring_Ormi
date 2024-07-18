package com.example.july08;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication(scanBasePackages = "com.example.july08.day17")
public class July08Application {
   /* @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String appName;

    @PostConstruct
    public void printConfig() {
        System.out.println("포트번호: " + port);
        System.out.println("애플리케이션 이름: " + appName);
    }*/
    public static void main(String[] args) {




        /*DispatcherServlet dispatcherServlet = new DispatcherServlet();
        String url = "/hello";

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(url);
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        dispatcherServlet.service(request, response);
        System.out.println(response.getContent());
        System.out.println("응답 내용" + response.getContent());*/


        SpringApplication.run(July08Application.class, args);
    }

}
