package com.winyoung.sb.springboot_web_h2.model;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String name;

    private String author;

    public Book(String name,String author){
        this.name=name;
        this.author=author;
    }
    
}
