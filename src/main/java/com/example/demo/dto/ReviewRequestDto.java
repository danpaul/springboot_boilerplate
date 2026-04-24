package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private Long id;
    private String title;
    private String content;
    private int rating;
    private Long userId;
    private Integer bookId;
}
