package com.example.demo.dto;

import com.example.demo.entity.Author;
import com.example.demo.enums.BookFormats;

import java.util.List;

public class BookResponseDto {
    private int id;
    private String name;
    private List<Author> authors;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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
