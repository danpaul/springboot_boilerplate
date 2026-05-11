package com.example.demo.domain.policy;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BorrowingPolicy {

    public void enforceBorrowingPolicy(User user, Book book) {
        enforceBookMustBeAvailable(book);
        enforceMembershipRequired(user);
        enforcePremiumMembershipForPremiumBook(user, book);
        enforceReferenceBooksCannotBeBorrowed(book);
        enforceBorrowLimit(user);
    }

    public void enforceBookMustBeAvailable(Book book) {
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed");
        }
    }

    public void enforceMembershipRequired(User user) {
        if (user.isMember()) {
            throw new IllegalStateException("Only members can borrow books");
        }
    }

    public void enforcePremiumMembershipForPremiumBook(User user, Book book) {
        if (book.isPremium() && !user.isPremiumMember()) {
            throw new IllegalStateException("Only premium members can borrow premium books");
        }
    }

    public void enforceReferenceBooksCannotBeBorrowed(Book book) {
        if (book.isReference()) {
            throw new IllegalStateException("Reference books cannot be borrowed");
        }
    }

    public void enforceBorrowLimit(User user) {
        if (user.getBorrowedBooks() != null && user.getBorrowedBooks().size() > 3) {
            throw new IllegalStateException("A user can borrow at most 3 books");
        }
    }
}
