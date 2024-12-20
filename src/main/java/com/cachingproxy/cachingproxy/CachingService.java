package com.cachingproxy.cachingproxy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CachingService {
    private final String url;
    private final CacheController cacheController;

    public CachingService(ApplicationArguments applicationArguments) {
        this.url = applicationArguments.getSourceArgs()[3];
        this.cacheController = new CacheController();
    }

    public ResponseEntity<String> getResponse(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        String body = null;
        String XCache = "MISS";
        String key = request.getRequestURI();

        if (cacheController.contains(key)) {
            XCache = "HIT";
            headers.add("X-Cache", XCache);
            return new ResponseEntity<>(cacheController.get(key), headers, HttpStatus.OK);
        }
        headers.add("X-Cache", XCache);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest APIRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + key))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> APIResponse = client.send(APIRequest, HttpResponse.BodyHandlers.ofString());
            body = APIResponse.body();
            if (APIResponse.statusCode() != 200) {
                return new ResponseEntity<>(body, headers, HttpStatus.valueOf(APIResponse.statusCode()));
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("IOException");
        } catch (InterruptedException e) {
            return ResponseEntity.status(500).body("InterruptedException");
        }

        cacheController.put(key, body);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
