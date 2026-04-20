package com.example.demo.dto;

import com.example.demo.enums.BookFormats;

import java.util.Optional;

public class BookRequestDtoDeLombok {
    private Optional<Integer> id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;

    public BookRequestDtoDeLombok() {
    }

    public Optional<Integer> getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public BookFormats getFormat() {
        return this.format;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setFormat(BookFormats format) {
        this.format = format;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookRequestDto)) return false;
        final BookRequestDto other = (BookRequestDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (this$isbn == null ? other$isbn != null : !this$isbn.equals(other$isbn)) return false;
        final Object this$format = this.getFormat();
        final Object other$format = other.getFormat();
        if (this$format == null ? other$format != null : !this$format.equals(other$format)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookRequestDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $format = this.getFormat();
        result = result * PRIME + ($format == null ? 43 : $format.hashCode());
        return result;
    }

    public String toString() {
        return "BookRequestDto(id=" + this.getId() + ", name=" + this.getName() + ", author=" + this.getAuthor() + ", isbn=" + this.getIsbn() + ", format=" + this.getFormat() + ")";
    }
}
