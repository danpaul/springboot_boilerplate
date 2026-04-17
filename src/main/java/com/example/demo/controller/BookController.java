package com.example.demo.controller;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 * @RestController is a Spring Boot annotation used to create RESTful web services.
 *
 * It combines two annotations:
 * - @Controller → marks the class as a web controller
 * - @ResponseBody → tells Spring to return data (like JSON or XML) directly
 *   instead of rendering a webpage (HTML)
 *
 * This means:
 * - Methods in this class handle HTTP requests (GET, POST, PUT, DELETE, etc.)
 * - The return values of these methods are automatically converted into JSON (by default)
 *
 * In short:
 * @RestController = "This class handles web requests and returns data directly."
 */
@RestController
// Handle all requests going to /books
@RequestMapping("books")
public class BookController {

    // declare a non-modifiable field to hold the book service
    final private BookService bookService;

    // Use constructor injection to automatically create the service
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books, long form request mapping
    // This good also be written as just:
    // @GetMapping("")
    @RequestMapping(value = "", method = RequestMethod.GET)
    Iterable<Book> getAll() {
        return this.bookService.findAll();
    }

    // get book by ID, note the dynamic route param is mapped to the input param
    // using the @PathVariable annotation
    @GetMapping("/{id}")
    // type path param gets injected directly in the controller param using @PathVariable
    Optional<Book> get(@PathVariable int id) {
        return this.bookService.findById(id);
    }

    // create a book
    @PostMapping("")
    // Request body automatically maps post data to book entity
    // note we are using a DTO to define the structure of the data we expect from the client
    Book create(@RequestBody BookRequestDto book) {
        System.out.println(book);
        return this.bookService.save(book);
    }

    // update a book
    @PutMapping("/{id}")
    // Request body automatically maps post data to book model
    // Path param (id) automatically maps using @PathVariable
    Book update(@RequestBody BookRequestDto book, @PathVariable int id) {
        return this.bookService.update(book, id);
    }

    // delete book by ID
    @DeleteMapping("/{id}")
    Object delete(@PathVariable int id) {
        this.bookService.delete(id);
        return new Object();
    }

}
