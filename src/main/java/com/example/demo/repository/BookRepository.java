package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;

// This is all we need to do to create a usable interface with standard CRUD!
// Look in the book service to see this in use
public interface BookRepository extends CrudRepository<Book, Integer> {

}
