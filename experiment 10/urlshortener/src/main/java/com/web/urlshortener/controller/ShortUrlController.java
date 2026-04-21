package com.web.urlshortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import com.web.urlshortener.model.Url;
import com.web.urlshortener.model.UrlResponse;
import com.web.urlshortener.model.UserDto;
import com.web.urlshortener.repository.UrlRepo;
import com.web.urlshortener.service.UrlServiceImpl;
import com.web.urlshortener.service.urlservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class ShortUrlController {
    @Autowired
    private UrlServiceImpl serv;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/addurl")
    public ResponseEntity<?> addurl(@RequestBody UserDto udto) {
        Url url = serv.createshorturl(udto);
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setShortUrl(url.getShorturl());
        urlResponse.setOrignalUrl(url.getOrignalUrl());
        urlResponse.setExpiryDate(url.getExpiryDate());
        urlResponse.setClickCount(url.getClickCount());
        return new ResponseEntity<>(urlResponse, HttpStatus.OK);
    }

    @GetMapping("/{shorturl}")
    public ResponseEntity<?> redirecttolongurl(@PathVariable String shorturl) throws IOException {
        Url url = serv.getencodedurl(shorturl);
        if (url != null) {
            if (url.getExpiryDate().isBefore(LocalDateTime.now())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            url.setClickCount(url.getClickCount() + 1);
            serv.persistshorturl(url);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        response.sendRedirect(url.getOrignalUrl());
        return null;
    }

    @GetMapping("/stats/{shorturl}")
    public ResponseEntity<?> getStats(@PathVariable String shorturl) {
        Url url = serv.getencodedurl(shorturl);
        if (url != null) {
            return ResponseEntity.ok(url.getClickCount());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count/{shorturl}")
    public ResponseEntity<?> getCount(@PathVariable String shorturl) {
        Url url = serv.getencodedurl(shorturl);
        if (url != null) {
            return ResponseEntity.ok(url.getClickCount());
        }
        return ResponseEntity.notFound().build();
    }
} 