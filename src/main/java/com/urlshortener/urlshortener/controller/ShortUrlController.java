package com.urlshortener.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urlshortener.urlshortener.model.ShortUrl;
import com.urlshortener.urlshortener.service.ShortUrlService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shorten")
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        ShortUrl shortUrl = shortUrlService.createShortUrl(url);
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortCode) {
        return shortUrlService.getOriginalUrl(shortCode)
                .map(shortUrl -> ResponseEntity.ok(shortUrl))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<?> updateShortUrl(@PathVariable String shortCode, @RequestBody Map<String, String> request) {
        String newUrl = request.get("url");
        return shortUrlService.updateShortUrl(shortCode, newUrl)
                .map(updatedUrl -> ResponseEntity.ok(updatedUrl))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String shortCode) {
        return shortUrlService.deleteShortUrl(shortCode)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<?> getUrlStats(@PathVariable String shortCode) {
        return shortUrlService.getOriginalUrl(shortCode)
                .map(shortUrl -> {
                    Map<String, Object> stats = new HashMap<>();
                    stats.put("id", shortUrl.getId());
                    stats.put("url", shortUrl.getUrl());
                    stats.put("shortCode", shortUrl.getShortCode());
                    stats.put("createdAt", shortUrl.getCreatedAt());
                    stats.put("updatedAt", shortUrl.getUpdatedAt());
                    stats.put("accessCount", shortUrl.getAccessCount());
                    return ResponseEntity.ok(stats);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
