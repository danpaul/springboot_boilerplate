package com.example.demo.entity;

import com.example.demo.enums.BookFormats;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "reviews")
public class Book {
    // define a primary ID column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // define additional columns with constraints...
    @Column(length = 128, nullable = false)
    private String name;

    private String author;

    // ensure the ISBN (book identifier) number is unique
    @Column(length = 16, nullable = false, unique = true)
    private String isbn;

    // use an enum for book format
    @Column(length = 16, nullable = false)
    private BookFormats format;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;
}
