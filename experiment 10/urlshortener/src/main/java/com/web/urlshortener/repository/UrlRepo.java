package com.web.urlshortener.repository;

import com.web.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends JpaRepository<Url,Long> {
    Url findByShorturl(String shorturl);
}
