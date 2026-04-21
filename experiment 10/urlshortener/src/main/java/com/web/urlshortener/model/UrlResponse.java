package com.web.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponse {
    private String orignalUrl;
    private String shortUrl;
    private LocalDateTime expiryDate;
    private int clickCount;
}
