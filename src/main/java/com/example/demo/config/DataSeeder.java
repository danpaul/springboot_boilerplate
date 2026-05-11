package com.example.demo.config;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.enums.BookFormats;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DataSeeder(
            UserRepository userRepository,
            BookRepository bookRepository
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0 || bookRepository.count() > 0) {
            return;
        }

        User admin = new User("admin", "admin@example.com");
        admin.setMember(true);
        admin.setPremiumMember(true);
        userRepository.save(admin);
        User alice = userRepository.save(new User("alice", "alice@example.com"));
        User bob = userRepository.save(new User("bob", "bob@example.com"));

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
        bookRepository.save(domainDrivenDesign);
    }
}
