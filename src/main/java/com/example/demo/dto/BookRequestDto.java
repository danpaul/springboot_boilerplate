package com.example.demo.dto;

import com.example.demo.enums.BookFormats;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Example of how to use validation annotations
// Note validation package added in pom.xml: spring-boot-starter-validation
// This include Jakarta Validation API
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private Integer id;

    @NotBlank(message = "Book name is required")
    @Size(max = 128, message = "Book name must be at most 128 characters")
    private String name;

    @NotBlank(message = "Author is required")
    @Size(max = 128, message = "Author name must be at most 128 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 16, message = "ISBN must be between 10 and 16 characters")
    private String isbn;

    @NotNull(message = "Book format is required")
    private BookFormats format;

    private boolean isBorrowed;
    private boolean isReference;
    private boolean isPremium;
}
