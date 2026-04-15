package com.example.demo.dto;

public record BookRequestDto(
         String title,
         String name,
         String author,
         String isbn,
         String format
) {}