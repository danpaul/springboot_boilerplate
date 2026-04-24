package com.example.demo.controller;

import com.example.demo.dto.ReviewRequestDto;
import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewController(
            ReviewService reviewService,
            ReviewMapper reviewMapper,
            UserRepository userRepository,
            BookRepository bookRepository
    ) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    Iterable<ReviewResponseDto> getAll() {
        return this.reviewMapper.toResponseDto(this.reviewService.findAll());
    }

    @GetMapping("/{id}")
    ReviewResponseDto get(@PathVariable Long id) {
        Optional<Review> review = this.reviewService.findById(id);
        if (review.isEmpty()) return new ReviewResponseDto();
        return this.reviewMapper.toResponseDto(review.get());
    }

    @PostMapping("")
    ReviewResponseDto create(@RequestBody ReviewRequestDto reviewRequestDto) {
        Review review = this.toEntity(reviewRequestDto);
        return this.reviewMapper.toResponseDto(this.reviewService.save(review));
    }

    @PutMapping("/{id}")
    ReviewResponseDto update(@RequestBody ReviewRequestDto reviewRequestDto, @PathVariable Long id) {
        reviewRequestDto.setId(id);
        Review review = this.toEntity(reviewRequestDto);
        return this.reviewMapper.toResponseDto(this.reviewService.update(review));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable Long id) {
        this.reviewService.delete(id);
        return new Object();
    }

    private Review toEntity(ReviewRequestDto reviewRequestDto) {
        User user = this.userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Book book = this.bookRepository.findById(reviewRequestDto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Review review = this.reviewMapper.toEntity(reviewRequestDto);
        review.setUser(user);
        review.setBook(book);
        return review;
    }
}
