package com.winyoung.sb.springboot_web_h2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.winyoung.sb.springboot_web_h2.model.Book;
import com.winyoung.sb.springboot_web_h2.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BookController {
  @Autowired
  BookService bookService;
  
  @GetMapping("/books")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Book> getAllBooks(@RequestParam(required = false) String name) {
    if (name == null)
      return bookService.findAll();
    else
      return bookService.findByNameContaining(name);
  }

  @GetMapping("/books/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Book> getBookById(@PathVariable("id") int id) {
    return bookService.findById(id);
  }

  @GetMapping("/books/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Book> addBook(@RequestParam(required = false) String name,String author) {
    return bookService.save(new Book(name, author));
  }

  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Book> createBook(@RequestBody Book book) {
    return bookService.save(new Book(book.getName(), book.getAuthor()));
  }

  @PutMapping("/books/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
    return bookService.update(id, book);
  }

  @DeleteMapping("/books/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteBook(@PathVariable("id") int id) {
    return bookService.deleteById(id);
  }

  @DeleteMapping("/books")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAllBooks() {
    return bookService.deleteAll();
  }


}
