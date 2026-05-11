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

    @BeforeEach
    void setUp() {
        borrowingPolicy = new BorrowingPolicy();

        memberUser = new User();
        memberUser.setMember(true);
        memberUser.setPremiumMember(false);
        memberUser.setBorrowedBooks(new ArrayList<>());

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

    @Test
    void enforceMembershipRequired_throwsForNonMember() {
        assertThrows(IllegalStateException.class, () -> borrowingPolicy.enforceMembershipRequired(nonMemberUser));
    }

    @Test
    void enforceMembershipRequired_allowsMember() {
        assertDoesNotThrow(() -> borrowingPolicy.enforceMembershipRequired(memberUser));
    }

    @Test
    void enforcePremiumMembershipForPremiumBook_throwsForNonPremiumMember() {
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforcePremiumMembershipForPremiumBook(memberUser, premiumBook)
        );
    }

    @Test
    void enforcePremiumMembershipForPremiumBook_allowsPremiumMember() {
        assertDoesNotThrow(
                () -> borrowingPolicy.enforcePremiumMembershipForPremiumBook(premiumMemberUser, premiumBook)
        );
    }

    @Test
    void enforceReferenceBooksCannotBeBorrowed_throwsForReferenceBook() {
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceReferenceBooksCannotBeBorrowed(referenceBook)
        );
    }

    @Test
    void enforceBookMustBeAvailable_throwsWhenBookAlreadyBorrowed() {
        regularBook.setBorrowed(true);
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBookMustBeAvailable(regularBook)
        );
    }

    @Test
    void enforceBorrowLimit_throwsAtLimit() {
        memberUser.setBorrowedBooks(new ArrayList<>(List.of(new Book(), new Book(), new Book())));
        assertThrows(IllegalStateException.class, () -> borrowingPolicy.enforceBorrowLimit(memberUser));
    }

    @Test
    void enforceBorrowingPolicy_throwsWhenAnyRuleFails() {
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBorrowingPolicy(nonMemberUser, regularBook)
        );
    }

    @Test
    void enforceBorrowingPolicy_allowsValidBorrow() {
        assertDoesNotThrow(() -> borrowingPolicy.enforceBorrowingPolicy(memberUser, regularBook));
    }

    @Test
    void enforceBorrowingPolicy_throwsWhenBookAlreadyBorrowed() {
        regularBook.setBorrowed(true);
        assertThrows(
                IllegalStateException.class,
                () -> borrowingPolicy.enforceBorrowingPolicy(memberUser, regularBook)
        );
    }
}
