package com.example.demo.controller;

import com.example.demo.dto.ReviewRequestDto;
import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.entity.Review;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(
            ReviewService reviewService,
            ReviewMapper reviewMapper
    ) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("")
    Iterable<ReviewResponseDto> getAll() {
        return this.reviewMapper.toResponseDto(this.reviewService.findAll());
    }

    @GetMapping("/{id}")
    ReviewResponseDto get(@PathVariable Long id) {
        Optional<Review> review = this.reviewService.findById(id);
        if (review.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        return this.reviewMapper.toResponseDto(review.get());
    }

    @PostMapping("")
    ReviewResponseDto create(@RequestBody ReviewRequestDto reviewRequestDto) {
        Review review = this.reviewService.toEntity(reviewRequestDto);
        return this.reviewMapper.toResponseDto(this.reviewService.save(review));
    }

    @PutMapping("/{id}")
    ReviewResponseDto update(@RequestBody ReviewRequestDto reviewRequestDto, @PathVariable Long id) {
        reviewRequestDto.setId(id);
        Review review = this.reviewService.toEntity(reviewRequestDto);
        return this.reviewMapper.toResponseDto(this.reviewService.update(review));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable Long id) {
        this.reviewService.delete(id);
        return new Object();
    }
}
