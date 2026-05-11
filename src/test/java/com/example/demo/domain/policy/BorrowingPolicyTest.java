package com.example.demo.domain.policy;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BorrowingPolicyTest {

    private BorrowingPolicy borrowingPolicy;
    private User memberUser;
    private User premiumMemberUser;
    private User nonMemberUser;
    private Book regularBook;
    private Book premiumBook;
    private Book referenceBook;

    // @BeforeEach is a JUnit 5 annotation that is used to mark a method as a test method.
    // It is used to initialize the test data before each test method is executed.
    @BeforeEach
    void setUp() {
        // Create fresh test data before each test so tests stay independent.
        borrowingPolicy = new BorrowingPolicy();

        memberUser = new User();
        memberUser.setMember(true);
        memberUser.setPremiumMember(false);
        memberUser.setBorrowedBooks(new ArrayList<>());
        // ...

        premiumMemberUser = new User();
        premiumMemberUser.setMember(true);
        premiumMemberUser.setPremiumMember(true);
        premiumMemberUser.setBorrowedBooks(new ArrayList<>());

        nonMemberUser = new User();
        nonMemberUser.setMember(false);
        nonMemberUser.setPremiumMember(false);
        nonMemberUser.setBorrowedBooks(new ArrayList<>());

        regularBook = new Book();
        regularBook.setPremium(false);
        regularBook.setReference(false);

        premiumBook = new Book();
        premiumBook.setPremium(true);
        premiumBook.setReference(false);

        referenceBook = new Book();
        referenceBook.setPremium(false);
        referenceBook.setReference(true);
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceMembershipRequired_throwsForNonMember() {
        // Non-members should be blocked by the membership rule.
        // assertThrows is a JUnit 5 assertion that is used to verify that a method throws an exception.
        assertThrows(IllegalStateException.class, () -> borrowingPolicy.enforceMembershipRequired(nonMemberUser));
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceMembershipRequired_allowsMember() {
        // Happy path: a regular member passes the membership check.
        // assertDoesNotThrow is a JUnit 5 assertion that is used to verify that a method does not throw an exception.
        assertDoesNotThrow(() -> borrowingPolicy.enforceMembershipRequired(memberUser));
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforcePremiumMembershipForPremiumBook_throwsForNonPremiumMember() {
        // Premium books require a premium membership.
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforcePremiumMembershipForPremiumBook(memberUser, premiumBook)
        );
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforcePremiumMembershipForPremiumBook_allowsPremiumMember() {
        assertDoesNotThrow(
                () -> borrowingPolicy.enforcePremiumMembershipForPremiumBook(premiumMemberUser, premiumBook)
        );
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceReferenceBooksCannotBeBorrowed_throwsForReferenceBook() {
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceReferenceBooksCannotBeBorrowed(referenceBook)
        );
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceBookMustBeAvailable_throwsWhenBookAlreadyBorrowed() {
        // Arrange: mark book as already borrowed, then verify borrowing is rejected.
        regularBook.setBorrowed(true);
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBookMustBeAvailable(regularBook)
        );
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceBorrowLimit_throwsAtLimit() {
        // Boundary test: exactly at the borrow limit should fail.
        memberUser.setBorrowedBooks(new ArrayList<>(List.of(new Book(), new Book(), new Book())));
        assertThrows(IllegalStateException.class, () -> borrowingPolicy.enforceBorrowLimit(memberUser));
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceBorrowingPolicy_throwsWhenAnyRuleFails() {
        // Integration-style policy test: one failing rule should reject the borrow.
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBorrowingPolicy(nonMemberUser, regularBook)
        );
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceBorrowingPolicy_allowsValidBorrow() {
        // End-to-end happy path: valid user + valid book should pass all rules.
        assertDoesNotThrow(() -> borrowingPolicy.enforceBorrowingPolicy(memberUser, regularBook));
    }

    // @Test is a JUnit 5 annotation that is used to mark a method as a test method.
    @Test
    void enforceBorrowingPolicy_throwsWhenBookAlreadyBorrowed() {
        regularBook.setBorrowed(true);
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBorrowingPolicy(memberUser, regularBook)
        );
    }
}
