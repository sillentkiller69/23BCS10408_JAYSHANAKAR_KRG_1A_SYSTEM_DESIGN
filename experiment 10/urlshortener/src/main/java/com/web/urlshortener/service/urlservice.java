package com.web.urlshortener.service;

import com.web.urlshortener.UrlshortenerApplication;
import com.web.urlshortener.model.Url;
import com.web.urlshortener.model.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface urlservice {
    public Url createshorturl(UserDto urlDto);
    public  Url persistshorturl(Url url);
    public Url getencodedurl(String url);
    public void deleteshorturl(Url  url);


}
