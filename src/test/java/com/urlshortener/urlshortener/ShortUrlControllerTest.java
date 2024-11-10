package com.urlshortener.urlshortener;

import com.urlshortener.urlshortener.model.ShortUrl;
import com.urlshortener.urlshortener.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @Test
    public void testCreateShortUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(1L);
        shortUrl.setUrl("https://www.example.com/some/long/url");
        shortUrl.setShortCode("abc123");

        Mockito.when(shortUrlService.createShortUrl(Mockito.any())).thenReturn(shortUrl);

        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"https://www.example.com/some/long/url\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url", is("https://www.example.com/some/long/url")))
                .andExpect(jsonPath("$.shortCode", is("abc123")));
    }

    @Test
    public void testRetrieveOriginalUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(1L);
        shortUrl.setUrl("https://www.example.com/some/long/url");
        shortUrl.setShortCode("abc123");

        Mockito.when(shortUrlService.getOriginalUrl("abc123")).thenReturn(Optional.of(shortUrl));

        mockMvc.perform(get("/shorten/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is("https://www.example.com/some/long/url")));
    }

    @Test
    public void testUpdateShortUrl() throws Exception {
        ShortUrl updatedUrl = new ShortUrl();
        updatedUrl.setId(1L);
        updatedUrl.setUrl("https://www.example.com/some/updated/url");
        updatedUrl.setShortCode("abc123");

        Mockito.when(shortUrlService.updateShortUrl(Mockito.eq("abc123"), Mockito.any()))
                .thenReturn(Optional.of(updatedUrl));

        mockMvc.perform(put("/shorten/abc123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"https://www.example.com/some/updated/url\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is("https://www.example.com/some/updated/url")));
    }

    @Test
    public void testDeleteShortUrl() throws Exception {
        Mockito.when(shortUrlService.deleteShortUrl("abc123")).thenReturn(true);

        mockMvc.perform(delete("/shorten/abc123"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUrlStatistics() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(1L);
        shortUrl.setUrl("https://www.example.com/some/long/url");
        shortUrl.setShortCode("abc123");
        shortUrl.setAccessCount(10);

        Mockito.when(shortUrlService.getOriginalUrl("abc123")).thenReturn(Optional.of(shortUrl));

        mockMvc.perform(get("/shorten/abc123/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessCount", is(10)));
    }
}
