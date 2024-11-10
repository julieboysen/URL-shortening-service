package com.urlshortener.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortener.urlshortener.model.ShortUrl;
import com.urlshortener.urlshortener.repository.ShortUrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ShortUrlService {
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public ShortUrl createShortUrl(String originalUrl) {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setUrl(originalUrl);
        shortUrl.setShortCode(generateShortCode());
        shortUrl.setCreatedAt(LocalDateTime.now());
        shortUrl.setUpdatedAt(LocalDateTime.now());
        shortUrl.setAccessCount(0);
        return shortUrlRepository.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String shortCode) {
        Optional<ShortUrl> shortUrl = shortUrlRepository.findByShortCode(shortCode);
        shortUrl.ifPresent(url -> url.setAccessCount(url.getAccessCount() + 1));
        shortUrl.ifPresent(url -> shortUrlRepository.save(url));
        return shortUrl;
    }

    public Optional<ShortUrl> updateShortUrl(String shortCode, String newUrl) {
        Optional<ShortUrl> shortUrl = shortUrlRepository.findByShortCode(shortCode);
        shortUrl.ifPresent(url -> {
            url.setUrl(newUrl);
            url.setUpdatedAt(LocalDateTime.now());
            shortUrlRepository.save(url);
        });
        return shortUrl;
    }

    public boolean deleteShortUrl(String shortCode) {
        Optional<ShortUrl> shortUrl = shortUrlRepository.findByShortCode(shortCode);
        shortUrl.ifPresent(shortUrlRepository::delete);
        return shortUrl.isPresent();
    }

    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }

}
