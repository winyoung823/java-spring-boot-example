package com.winyoung.sb.springboot_web_h2.repository;


import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.winyoung.sb.springboot_web_h2.model.Book;

import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends R2dbcRepository<Book, Integer>{
  Flux<Book> findByNameContaining(String title);  
}

