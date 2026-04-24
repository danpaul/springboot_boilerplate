package com.example.demo.service;

import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Iterable<Review> findAll() {
        return this.reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id) {
        return this.reviewRepository.findById(id);
    }

    public Review save(Review review) {
        return this.reviewRepository.save(review);
    }

    public Review update(Review review) {
        return this.reviewRepository.save(review);
    }

    public void delete(Long id) {
        this.reviewRepository.deleteById(id);
    }
}
