package com.cachingproxy.cachingproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CachingProxyApplication {

    public static void main(String[] args) {
        System.setProperty("server.port", args[1]);
        SpringApplication.run(CachingProxyApplication.class, args);
    }
}
