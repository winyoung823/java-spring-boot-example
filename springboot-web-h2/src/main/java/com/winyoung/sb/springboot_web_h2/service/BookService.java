package com.winyoung.sb.springboot_web_h2.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winyoung.sb.springboot_web_h2.model.Book;
import com.winyoung.sb.springboot_web_h2.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

  @Autowired
  BookRepository bookRepository;

  public Flux<Book> findAll() {
    return bookRepository.findAll();
  }

  public Flux<Book> findByNameContaining(String name) {
    return bookRepository.findByNameContaining(name);
  }

  public Mono<Book> findById(int id) {
    return bookRepository.findById(id);
  }

  public Mono<Book> save(Book Book) {
    return bookRepository.save(Book);
  }

  public Mono<Book> update(int id, Book Book) {
    return bookRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
        .flatMap(optionalBook -> {
          if (optionalBook.isPresent()) {
            Book.setId(id);
            return bookRepository.save(Book);
          }

          return Mono.empty();
        });
  }

  public Mono<Void> deleteById(int id) {
    return bookRepository.deleteById(id);
  }

  public Mono<Void> deleteAll() {
    return bookRepository.deleteAll();
  }

}

