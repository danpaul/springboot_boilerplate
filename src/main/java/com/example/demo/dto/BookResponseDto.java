package com.example.demo.dto;

import com.example.demo.enums.BookFormats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private int id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
    private List<ReviewResponseDto> reviews;
}

/**
 * Teaching example only: this is roughly what Lombok generates for BookResponseDto:
 */
class BookResponseDtoDeLombokExample {
    private int id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
    private List<ReviewResponseDto> reviews;

    public BookResponseDtoDeLombokExample() {
    }

    public BookResponseDtoDeLombokExample(
            int id,
            String name,
            String author,
            String isbn,
            BookFormats format,
            List<ReviewResponseDto> reviews
    ) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.format = format;
        this.reviews = reviews;
    }

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

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookResponseDtoDeLombokExample that)) return false;
        return id == that.id
                && Objects.equals(name, that.name)
                && Objects.equals(author, that.author)
                && Objects.equals(isbn, that.isbn)
                && format == that.format
                && Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, isbn, format, reviews);
    }

    @Override
    public String toString() {
        return "BookResponseDtoDeLombokExample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", format=" + format +
                ", reviews=" + reviews +
                '}';
    }
}
