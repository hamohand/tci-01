package com.med.tci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class TciApplication {

    public static void main(String[] args) {

        SpringApplication.run(TciApplication.class, args);
        System.out.println("-- tci started port:8080 --");
    }

    @EventListener
    public void onAppStart(WebApplicationContext ctx) {
        System.out.println("Application a démarré sur le port " + ctx.getApplicationName());
    }


}
