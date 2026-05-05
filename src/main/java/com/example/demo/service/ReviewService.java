package com.example.demo.service;

import com.example.demo.dto.ReviewRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper,
            UserRepository userRepository,
            BookRepository bookRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
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
        Review existingReview = this.reviewRepository.findById(review.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new AccessDeniedException("Authentication required");
        }

        User currentUser = (User) authentication.getPrincipal();
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        boolean isOwner = existingReview.getUser() != null
                && existingReview.getUser().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You can only update your own reviews");
        }

        // Preserve ownership and target book during update to prevent spoofing.
        review.setUser(existingReview.getUser());
        review.setBook(existingReview.getBook());

        return this.reviewRepository.save(review);
    }

    public Review toEntity(ReviewRequestDto reviewRequestDto) {
        User user = this.userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = this.bookRepository.findById(reviewRequestDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Review review = this.reviewMapper.toEntity(reviewRequestDto);
        review.setUser(user);
        review.setBook(book);
        return review;
    }

    public void delete(Long id) {
        this.reviewRepository.deleteById(id);
    }
}
