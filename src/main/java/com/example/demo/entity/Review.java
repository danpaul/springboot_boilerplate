package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "reviews",
        indexes = {
                // index join columns (foreign keys)
                // likely automatically indexed by underlying DB but not guaranteed by JPA
                @Index(name = "idx_user", columnList = "user_id"),
                @Index(name = "idx_book", columnList = "book_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "book"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String title;
    private String content;

    // many reviews map to one user
    @ManyToOne
    // optional (recommended) specify join column
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
