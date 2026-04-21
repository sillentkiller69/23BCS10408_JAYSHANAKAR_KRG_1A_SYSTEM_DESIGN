package com.web.urlshortener.service;

import com.web.urlshortener.model.Url;
import com.web.urlshortener.model.UserDto;
import com.web.urlshortener.repository.UrlRepo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class UrlServiceImpl implements urlservice{
    @Autowired
   UrlRepo urlRepo;


    @Override
    public Url createshorturl(UserDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getOrignalurl())){
            if (StringUtils.isNotBlank(urlDto.getCustomName())) {
                String custom = urlDto.getCustomName().trim();
                if (urlRepo.findByShorturl(custom) == null) {
                    Url persisturl=new Url();
                    persisturl.setOrignalUrl(urlDto.getOrignalurl());
                    persisturl.setShorturl(custom);
                    persisturl.setCreationDate(LocalDateTime.now());
                    persisturl.setExpiryDate(getExpirydate(urlDto.getExpiryDate(),persisturl.getCreationDate()));
                    return persistshorturl(persisturl);
                }
            }
            // else, generate
            Url persisturl=new Url();
            persisturl.setOrignalUrl(urlDto.getOrignalurl());
            persisturl.setCreationDate(LocalDateTime.now());
            persisturl.setExpiryDate(getExpirydate(urlDto.getExpiryDate(),persisturl.getCreationDate()));
            Url saved = persistshorturl(persisturl);
            String encodedurl = encodeBase62(saved.getId());
            saved.setShorturl(encodedurl);
            return persistshorturl(saved);
        }

        return null;
    }

    private LocalDateTime getExpirydate(String expiryDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expiryDate)){
            return creationDate.plusSeconds(60);
        }
        return LocalDateTime.parse(expiryDate);

    }

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String encodeBase62(long value) {
        if (value == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }

    @Override
    public Url persistshorturl(Url url) {

        return urlRepo.save(url);
    }

    @Override
    public Url getencodedurl(String url) {

        return urlRepo.findByShorturl(url);
    }

    @Override
    public void deleteshorturl(Url url) {
        urlRepo.delete(url);

    }
}
