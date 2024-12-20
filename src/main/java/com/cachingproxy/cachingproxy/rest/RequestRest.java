package com.cachingproxy.cachingproxy.rest;

import com.cachingproxy.cachingproxy.CachingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestRest {
    private CachingService cachingService;

    public RequestRest(CachingService cachingService) {
        this.cachingService = cachingService;
    }

    @GetMapping("/**")
    public ResponseEntity<String> getResponse(HttpServletRequest request) {
        return cachingService.getResponse(request);
    }
}
