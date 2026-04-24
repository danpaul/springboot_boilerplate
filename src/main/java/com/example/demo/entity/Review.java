package com.example.demo.entity;

import jakarta.persistence.*;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
