package com.example.demo.dto;

import com.example.demo.enums.BookFormats;

public class BookResponseDtoPojo {

    private int id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
    
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookFormats getFormat() {
        return format;
    }

    public void setFormat(BookFormats format) {
        this.format = format;
    }
}
