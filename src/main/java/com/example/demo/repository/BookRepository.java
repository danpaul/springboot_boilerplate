package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.enums.BookFormats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Extending {@link CrudRepository} gives you basic persistence for free: {@code save},
 * {@code findById}, {@code findAll}, {@code count}, {@code deleteById}, and more.
 * <p>
 * Spring Data JPA also derives implementations from method <em>names</em> (query methods),
 * runs {@code @Query} methods you declare, and can apply {@link Pageable} / {@code Sort} when
 * you add those parameters—no implementation class is required.
 */
public interface BookRepository extends CrudRepository<Book, Integer> {

    // --- Query by property: Spring parses "findBy" + property name (matches getter "getIsbn") ---

    /**
     * Returns at most one row because {@code isbn} is unique on {@link Book}.
     * Use {@link Optional} when a match is not guaranteed.
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * "Containing" adds SQL {@code LIKE %value%}; "IgnoreCase" applies case-insensitive matching.
     * Useful for simple search boxes driven by user input.
     */
    List<Book> findByNameContainingIgnoreCase(String nameFragment);

    /**
     * Enum properties map to their stored column; Spring binds the enum value for you.
     */
    List<Book> findByFormat(BookFormats format);

    // --- Keyword prefixes: exists, count, delete ---

    /**
     * Cheaper than loading the entity when you only need to know if an ISBN is already taken.
     */
    boolean existsByIsbn(String isbn);

    /**
     * Returns how many books use the given format (aggregates in the database).
     */
    long countByFormat(BookFormats format);

    /**
     * Removes every row whose {@code isbn} matches. Use with care in production APIs.
     */
    void deleteByIsbn(String isbn);

    // --- Sorting baked into the method name (OrderBy...Asc/Desc) ---

    /**
     * Same filter as {@link #findByFormat(BookFormats)} but results are ordered by title.
     */
    List<Book> findByFormatOrderByNameAsc(BookFormats format);

    // --- Pagination: pass a Pageable; Spring returns a Page slice + total count query ---

    /**
     * Paged listing for a format—handy for tables that load one page at a time.
     * <p>
     * Callers usually pass a {@link org.springframework.data.domain.PageRequest}, for example
     * {@code PageRequest.of(0, 20)} for the first page of 20 rows (0-based page index), or
     * {@code PageRequest.of(2, 10, Sort.by("name").ascending())} for page 2, 10 per page,
     * sorted by book name ascending. Any {@link Pageable} implementation works.
     */
    Page<Book> findByFormat(BookFormats format, Pageable pageable);

    // --- Explicit JPQL when names get awkward or you want a custom projection/filter ---

    /**
     * Custom JPQL; {@link Param} binds {@code :term} by name. Demonstrates escape from
     * strict derived-query naming when you need clearer SQL/JPQL.
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Book> searchByNameJpql(@Param("term") String term);
}
