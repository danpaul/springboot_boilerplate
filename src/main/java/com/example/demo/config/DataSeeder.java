package com.example.demo.config;

import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.enums.BookFormats;
import com.example.demo.enums.Roles;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public DataSeeder(
            UserRepository userRepository,
            BookRepository bookRepository,
            ReviewRepository reviewRepository,
            AuthService authService
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.authService = authService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0 || bookRepository.count() > 0) {
            return;
        }

        User admin = new User("admin", "admin", List.of(Roles.ROLE_USER, Roles.ROLE_ADMIN));
        admin.setEmail("admin@example.com");

        User alice = new User("alice", "alice123", List.of(Roles.ROLE_USER));
        alice.setEmail("alice@example.com");

        User bob = new User("bob", "bob123", List.of(Roles.ROLE_USER));
        bob.setEmail("bob@example.com");

        admin = authService.register(admin);
        alice = authService.register(alice);
        bob = authService.register(bob);

        Book cleanCode = new Book();
        cleanCode.setName("Clean Code");
        cleanCode.setAuthor("Robert C. Martin");
        cleanCode.setIsbn("9780132350884");
        cleanCode.setFormat(BookFormats.Hardcover);

        Book pragmaticProgrammer = new Book();
        pragmaticProgrammer.setName("The Pragmatic Programmer");
        pragmaticProgrammer.setAuthor("Andrew Hunt, David Thomas");
        pragmaticProgrammer.setIsbn("9780135957059");
        pragmaticProgrammer.setFormat(BookFormats.Paperback);

        Book domainDrivenDesign = new Book();
        domainDrivenDesign.setName("Domain-Driven Design");
        domainDrivenDesign.setAuthor("Eric Evans");
        domainDrivenDesign.setIsbn("9780321125217");
        domainDrivenDesign.setFormat(BookFormats.EBook);

        cleanCode = bookRepository.save(cleanCode);
        pragmaticProgrammer = bookRepository.save(pragmaticProgrammer);
        domainDrivenDesign = bookRepository.save(domainDrivenDesign);

        Review review1 = new Review();
        review1.setRating(5);
        review1.setTitle("Practical and timeless");
        review1.setContent("Great guidance for writing maintainable code.");
        review1.setUser(alice);
        review1.setBook(cleanCode);

        Review review2 = new Review();
        review2.setRating(4);
        review2.setTitle("Excellent tips");
        review2.setContent("Very actionable advice with strong real-world examples.");
        review2.setUser(bob);
        review2.setBook(cleanCode);

        Review review3 = new Review();
        review3.setRating(5);
        review3.setTitle("Must-read for developers");
        review3.setContent("One of the best books on software craftsmanship.");
        review3.setUser(admin);
        review3.setBook(pragmaticProgrammer);

        Review review4 = new Review();
        review4.setRating(4);
        review4.setTitle("Deep and insightful");
        review4.setContent("Dense, but invaluable for understanding complex domains.");
        review4.setUser(alice);
        review4.setBook(domainDrivenDesign);

        reviewRepository.saveAll(List.of(review1, review2, review3, review4));
    }
}
