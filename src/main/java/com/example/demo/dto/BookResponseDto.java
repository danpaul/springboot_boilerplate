package com.example.demo.dto;

import com.example.demo.enums.BookFormats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private int id;
    private String name;
    private String author;
    private String isbn;
    private BookFormats format;
    private boolean isBorrowed;
    private boolean isReference;
    private boolean isPremium;
}
