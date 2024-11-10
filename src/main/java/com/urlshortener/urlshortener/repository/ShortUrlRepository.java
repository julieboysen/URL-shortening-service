package com.urlshortener.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urlshortener.urlshortener.model.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);
}