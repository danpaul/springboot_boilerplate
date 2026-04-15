package com.example.demo.entity;

import com.example.demo.enums.BookFormats;
import jakarta.persistence.*;

// Define an entity. JPA will automatically sync this with an underlying table
@Entity
@Table(
        // explicitly define the DB table used
        name="books",
        // define indexes (important for columns we expect to query against)
        indexes = {
                // index the author column
                @Index(name="idx_author", columnList = "author"),
                // index the ISBN number
                @Index(name="idx_isbn", columnList = "isbn"),
                // compound index to find all author's work of a specific format
                @Index(name="idx_author_format", columnList = "author, format")
        }
)
public class Book {
    // define a primary ID column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // define additional columns with constraints...
    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 128, nullable = false)
    private String author;

    // ensure the ISBN (book identifier) number is unique
    @Column(length = 16, nullable = false, unique = true)
    private String isbn;

    // use an enum for book format
    @Column(length = 16, nullable = false)
    private BookFormats format;

    // getters and setters
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
