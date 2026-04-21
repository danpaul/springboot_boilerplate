package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This is all we need to do to create a usable interface with standard CRUD!
// Look in the book service to see this in use
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
