package com.tang.contactservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Tang
 * Application runner, with an embedded tomcat server
 */
@SpringBootApplication
public class App {
    public static void main( String[] args ){
    	SpringApplication.run(App.class, args);
    }
    
}
