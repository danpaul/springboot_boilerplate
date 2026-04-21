package com.example.demo.entity;

import com.example.demo.enums.BookFormats;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Define an entity. JPA will automatically sync this with an underlying table
@Entity
@Table(
        // explicitly define the DB table used
        name = "books",
        // define indexes (important for columns we expect to query against)
        indexes = {
                // index the ISBN number
                @Index(name = "idx_isbn", columnList = "isbn"),
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

    // only save and update operations will be persisted
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // it is possible to specify columns for joins, if we want greater control
    // otherwise, these are created automatically
    //    @JoinTable(
    //            name = "book_author",
    //            joinColumns = @JoinColumn(name = "book_id"),
    //            inverseJoinColumns = @JoinColumn(name = "author_id")
    //    )
    private List<Author> authors = new ArrayList<>();

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
