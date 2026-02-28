package com.mipt.popikovdmitriy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Entry point for the Todo List Manager application.
 *
 * <p>
 * Bootstraps the Spring Boot context with AspectJ auto-proxy support enabled,
 * allowing AOP-based cross-cutting concerns such as logging.</p>
 *
 * @see com.mipt.popikovdmitriy.aspect.LoggingAspect
 */
@SpringBootApplication
@EnableAspectJAutoProxy

public class ManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class, args);
    }

}
