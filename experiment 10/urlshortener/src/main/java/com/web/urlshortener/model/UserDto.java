package com.web.urlshortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String orignalurl;
    private String expiryDate;
    private String customName;



}
